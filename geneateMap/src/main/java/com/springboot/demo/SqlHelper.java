package com.springboot.demo;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 */
public class SqlHelper {
    //基本数据配置
//    private String packageOutPath = "com.xxx.com.entity.system";// 指定实体生成所在包的路径
    private String mainPackageOutPath = "";// 指定实体生成所在包的路径
    private String authorName = "CDN";// 作者名字
    private static List<String> tablenameList;// 表名
    private String[] colnames; // 列名数组
    private List<String> colComments; //列注释
    private String[] colTypes; // 列名类型数组
    private String version = "V0.01"; // 版本
    private int[] colSizes; // 列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*
    private boolean f_lang = false; // 是否需要导入包java.sql.*
    private String defaultPath = "/src/main/java/";
    // 数据库连接
    private static String URL = "";
    private static String NAME = "";
    private static String PASS = "";
    private static String DRIVER = "com.mysql.jdbc.Driver";


    /**
     * 获取数据库下的所有表名
     */
    public static void getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, NAME, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(conn.getCatalog(), "%", "%", new String[]{"TABLE"});
            while (rs.next()) {
                System.out.println(rs.getString(3));
                tableNames.add(rs.getString(3));
            }
            System.out.println("##############共计" + tableNames.size() + "个表=################");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        tablenameList = tableNames;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }


    /**
     * 根据数据库连接和表明获取主键名(利用正则表达式)
     *
     * @param table 数据库中的表名
     * @return 执行成功返回一个主键名的字符数组，否则返回null或抛出一个异常
     */
    public static String[] getPrimaryKey(String table, Connection conn) {
        try {
            conn = DriverManager.getConnection(URL, NAME, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "SHOW CREATE TABLE " + table;
        try {

            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {

                // 正则匹配数据
                Pattern pattern = Pattern.compile("PRIMARY KEY \\(\\`(.*)\\`\\)");
                Matcher matcher = pattern.matcher(rs.getString(2));
                matcher.find();
                String data = matcher.group();
                // 过滤对于字符
                data = data.replaceAll("\\`|PRIMARY KEY \\(|\\)", "");
                // 拆分字符
                String[] stringArr = data.split(",");

                return stringArr;
            }
        } catch (Exception e) {
            if (e instanceof IllegalStateException) {
                System.out.println("\n\t>>>>>>>>>>>>\n\t|请设置主键|\n\t<<<<<<<<<<<<");
            } else {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * 构造函数
     */
    public SqlHelper(String dataBaseUrl, String dataBaseUserName, String dataBasePwd, String packagePath) {
        URL = "jdbc:mysql://" + dataBaseUrl + "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&allowMultiQueries=true";
        NAME = dataBaseUserName;
        PASS = dataBasePwd;
        mainPackageOutPath = packagePath;
//        获取所有的表名
        getTableNames();


//        创建文件夹
        String rootPath = new File("").getAbsolutePath() + this.defaultPath + this.mainPackageOutPath.replace(".", "/");
        String controllerPath = rootPath + "/controller";
        String entityPath = rootPath + "/entity";
        String mapperPath = rootPath + "/mapper";
        String providerPath = rootPath + "/mapper/provider";
        String servicePath = rootPath + "/service";
        String serviceImplPath = rootPath + "/service/impl";
        String utilsPath = rootPath + "/utils";
        String mybatisPath = new File("").getAbsolutePath() + "/src/main/resources/mybatis";
        String generatePath = new File("").getAbsolutePath() + "/src/main/resources/genarate";
        String resourcesPath = new File("").getAbsolutePath() + "/src/main/resources";

        CreateFileUtil.createDir(controllerPath);
        CreateFileUtil.createDir(entityPath);
        CreateFileUtil.createDir(mapperPath);
        CreateFileUtil.createDir(providerPath);
        CreateFileUtil.createDir(servicePath);
        CreateFileUtil.createDir(serviceImplPath);
        CreateFileUtil.createDir(utilsPath);
        CreateFileUtil.createDir(mybatisPath);
        CreateFileUtil.createDir(generatePath);


        new Thread(new Runnable() {
            @Override
            public void run() {
                //        生成工具类，不用循环
                generateUtils(utilsPath + "/");
                generateOther(resourcesPath + "/");
            }
        }).start();


        // 创建连接
        Connection con;
//        统计表的数量
        int count = 0;
        for (String tablename : tablenameList) {
            String controllerFinalPath = controllerPath + "/" + initcap(lineToHump(tablename)) + "Controller.java";
            String entityFinalPath = entityPath + "/" + initcap(lineToHump(tablename)) + ".java";
            String mapperFinalPath = mapperPath + "/" + initcap(lineToHump(tablename)) + "Mapper.java";
            String providerFinalPath = providerPath + "/" + initcap(lineToHump(tablename)) + "Provider.java";
            String serviceFinalPath = servicePath + "/" + initcap(lineToHump(tablename)) + "Service.java";
            String serviceImplFinalPath = serviceImplPath + "/" + initcap(lineToHump(tablename)) + "ServiceImpl.java";
            String mybatislFinalPath = mybatisPath + "/" + initcap(lineToHump(tablename)) + "Mapper.xml";

            // 查要生成实体类的表
            String sql = "select * from " + tablename;
            PreparedStatement pStemt = null;
            try {
                try {
                    Class.forName(DRIVER);
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                con = DriverManager.getConnection(URL, NAME, PASS);
                pStemt = con.prepareStatement(sql);
                ResultSetMetaData rsmd = pStemt.getMetaData();
                int size = rsmd.getColumnCount(); // 统计列
                colnames = new String[size];
                colTypes = new String[size];
                colComments = getColumnComments(tablename);
                colSizes = new int[size];
                for (int i = 0; i < size; i++) {
                    colnames[i] = rsmd.getColumnName(i + 1);
                    colTypes[i] = rsmd.getColumnTypeName(i + 1);
                    //自动生成包配置
                    if (colTypes[i].equalsIgnoreCase("datetime")) {
                        f_util = true;
                    }
                    if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")
                            || colTypes[i].equalsIgnoreCase("datetime") || colTypes[i].equalsIgnoreCase("time")
                            || colTypes[i].equalsIgnoreCase("date") || colTypes[i].equalsIgnoreCase("datetime2")) {
                        f_sql = true;
                    }
                    if (colTypes[i].equalsIgnoreCase("int")) {
                        f_lang = true;
                    }
                    colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
                }


//                寻找主键
                count++;
                String[] primaryKey = getPrimaryKey(tablename, con);
                boolean primaryKeyExist = false;
                String primaryKeyName = "";
                String primaryKeyType = "";
                if (primaryKey != null && primaryKey.length > 0) {
                    primaryKeyName = primaryKey[0];
                    for (int i = 0; i < colnames.length; i++) {
                        if (primaryKey[0].equals(colnames[i])) {
                            primaryKeyType = colTypes[i];
                            primaryKeyExist = true;
                        }
                    }
                }


//                如果存在主键标识
                if (primaryKeyExist) {
//                    -----------------正式生成-------------------------
                    new Thread(() -> {//生成实体
                        String entityContent = createEntity(tablename);
                        startWriteEntity(entityFinalPath, entityContent);
                    }).start();

                    new Thread(() -> {//生成mapper
                        String mapperContent = getMapperContent(tablename, colnames, colTypes);
                        CreateFileUtil.createFile(mapperFinalPath, mapperContent);
                    }).start();

                    String finalPrimaryKeyName = primaryKeyName;
                    String finalPrimaryKeyType = primaryKeyType;
                    new Thread(() -> {//生成privoder
                        String providerContent = getProviderContent(tablename, finalPrimaryKeyName, finalPrimaryKeyType);
                        CreateFileUtil.createFile(providerFinalPath, providerContent);
                    }).start();

                    new Thread(() -> {//生成service
                        String serviceContent = getServiceContent(tablename);
                        CreateFileUtil.createFile(serviceFinalPath, serviceContent);
                    }).start();

                    new Thread(() -> {//生成serviceImpl
                        String serviceImplContent = getServiceImpl(tablename, finalPrimaryKeyName, finalPrimaryKeyType);
                        CreateFileUtil.createFile(serviceImplFinalPath, serviceImplContent);
                    }).start();

                    new Thread(() -> {//生成controller
                        String controllerContent = getControllerContent(tablename, finalPrimaryKeyName, finalPrimaryKeyType);
                        CreateFileUtil.createFile(controllerFinalPath, controllerContent);
                    }).start();
                    System.out.println("第" + count + "个表生成完毕，生成路径为：" + entityFinalPath);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * desc:  生成utils下的类
     * param:
     * author: CDN
     * date: 2019/9/14
     */
    public void generateUtils(String utilsFinalPath) {
        Map<String, String> map = new HashMap<>();
        String JsonResultContent = getJsonResultContent();
        String StrUtilsContent = getStrUtils();
        String MyControllerAdviceContent = MyControllerAdvice();
        String myException = getMyException();
        map.put("JsonResult.java", JsonResultContent);
        map.put("MyException.java", myException);
        map.put("StrUtils.java", StrUtilsContent);
        map.put("MyControllerAdvice.java", MyControllerAdviceContent);

        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            CreateFileUtil.createFile(utilsFinalPath + stringStringEntry.getKey(), stringStringEntry.getValue());
        }

    }

    /**
     * desc: 生成resources目录下的文件
     * param:
     * author: CDN
     * date: 2019/9/21
     */
    public void generateOther(String resourcesPath) {
        String README = Model.README;

//        application.yml配置文件
        String applicationYml = Model.applicationYml;
        applicationYml = applicationYml.replaceAll("XXXURLXXX", URL);
        applicationYml = applicationYml.replaceAll("XXXusernameXXX", NAME);
        applicationYml = applicationYml.replaceAll("XXXrootXXX", PASS);

//        generatorConfig.xml
        String generateContent = getGenerateContent();

        Map<String, String> map = new HashMap<>();
        map.put("README.txt", README);
        map.put("application.yml", applicationYml);
        map.put("generatorConfig.xml", generateContent);

        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            if ("generatorConfig.xml".equalsIgnoreCase(stringStringEntry.getKey())) {
                CreateFileUtil.createFile(resourcesPath  + "/genarate/" + stringStringEntry.getKey(), generateContent);
            } else {
                CreateFileUtil.createFile(resourcesPath + stringStringEntry.getKey(), stringStringEntry.getValue());
            }
        }


    }

    /**
     * desc:
     * param:
     * return:
     * author: CDN
     * date: 2019/9/25
     */
    public String getGenerateContent() {
        String generate_before = Model.GENERATE_before;
        String generateMiddle = Model.GENERATE_MIDDLE;
        String generate_end = Model.GENERATE_End;

        generate_before = generate_before.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        generate_before = generate_before.replaceAll("XXXURLXXX", URL);
        generate_before = generate_before.replaceAll("XXXusernameXXX", NAME);
        generate_before = generate_before.replaceAll("XXXrootXXX", PASS);

        for (String tableName : tablenameList) {
            String tem = generateMiddle;
            tem = tem.replaceAll("XXXtableNameXXX", tableName);
            tem = tem.replaceAll("XXXDeptXXX", initcap(lineToHump(tableName)));
            generate_before += tem;
        }
        return generate_before + generate_end;
    }


    /**
     * desc: 组织 JsonResult
     * param:
     * return:
     * author: CDN
     * date: 2019/9/14
     */
    public String getJsonResultContent() {
        String JsonResult = Model.JsonResult;
        JsonResult = JsonResult.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        return JsonResult;
    }

    /**
     * desc: 组织StrUtil
     * param:
     * return:
     * author: CDN
     * date: 2019/9/14
     */
    public String getStrUtils() {
        String strUtils = Model.StrUtil;
        strUtils = strUtils.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        return strUtils;
    }

    /**
     * desc: 组织全局异常处理
     * param:
     * return:
     * author: CDN
     * date: 2019/9/25
     */
    public String MyControllerAdvice() {
        String MyControllerAdvice = Model.MyControllerAdvice;
        MyControllerAdvice = MyControllerAdvice.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        return MyControllerAdvice;
    }


    /**
     * desc: 组织myExcepiion
     * param:
     * return:
     * author: CDN
     * date: 2019/9/14
     */
    public String getMyException() {
        String myExcepiion = Model.MyException;
        myExcepiion = myExcepiion.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        return myExcepiion;
    }

    /**
     * desc:组织Controller
     * param:
     * return:
     * author: CDN
     * date: 2019/9/14
     */
    private String getControllerContent(String tableName, String primaryKeyName, String primaryKeyType) {
        String Controller_before = Model.Controller_before;
        String Controller_LogicDelete = Model.Controller_LogicDelete;
        String Controller_End = Model.Controller_End;
        Controller_before = Controller_before.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        Controller_before = Controller_before.replaceAll("ENTITYNAMEXXX", initcap(lineToHump(tableName)));
        Controller_before = Controller_before.replaceAll("TABLENAME_CDN", lineToHump(tableName));

        //        查找是否有逻辑删除的字段，（注释中含有logic）
        Controller_before = logicDelete(Controller_before, Controller_LogicDelete, "logic") + Controller_End;


        Controller_before = Controller_before.replaceAll("TIMECDNXX", getNowTime());
        return Controller_before;
    }

    /**
     * desc: 组织serviceImpl
     * param:
     * return:
     * author: CDN
     * date: 2019/9/14
     */
    private String getServiceImpl(String tableName, String primaryKeyName, String primaryKeyType) {
        String serviceImpl = Model.ServiceImpl_before;
        String serviceImpl_logicDelete = Model.ServiceImpl_logicDelete;
        String serviceImpl_end = Model.ServiceImpl_end;

        if (primaryKeyName != null && !"".equals(primaryKeyName) && primaryKeyType.equalsIgnoreCase("VARCHAR")) {
            String temp = "map.put(\"WQWQXX\", UUID.randomUUID());";
            temp = temp.replaceAll("WQWQXX", lineToHump(primaryKeyName));
            serviceImpl = serviceImpl.replaceAll("XVXVXVX", temp);
        } else {
            serviceImpl = serviceImpl.replaceAll("XVXVXVX", "");
        }

//        判断是否有逻辑删除的状态字段
        String status = "map.put(\"status\", 1);" +
                "        ";
        for (int i = 0; i < colnames.length; i++) {
            if (colComments.get(i).equalsIgnoreCase("logic")) {
                status = status.replaceAll("status", lineToHump(colnames[i]));
                serviceImpl = serviceImpl.replaceAll("XXXSTATUSXXX", status);
                break;
            }
//            如果到最后一个字段都没有找到逻辑删除字段，则把XXXSTATUSXXX替换为空
            if (i == colnames.length - 1 && serviceImpl.contains("XXXSTATUSXXX")) {
                serviceImpl = serviceImpl.replaceAll("XXXSTATUSXXX", "");
            }
        }

        serviceImpl = serviceImpl.replaceAll("TIMECDNXX", getNowTime());

        //        查找是否有逻辑删除的字段，（注释中含有logic）
        serviceImpl = logicDelete(serviceImpl, serviceImpl_logicDelete, "logic");

        serviceImpl = serviceImpl.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        serviceImpl = serviceImpl.replaceAll("ENTITYNAMEXXX", initcap(lineToHump(tableName)));
        serviceImpl = serviceImpl.replaceAll("##XXXX##", lineToHump(tableName));
        serviceImpl = serviceImpl.replaceAll("primaryKeyXX", lineToHump(primaryKeyName));
        serviceImpl = serviceImpl.replaceAll("PrimaryKeyXX", initcap(lineToHump(primaryKeyName)));


        serviceImpl = serviceImpl + serviceImpl_end;
        return serviceImpl;
    }


    /**
     * desc: 组织service
     * param:
     * return:
     * author: CDN
     * date: 2019/9/14
     */
    private String getServiceContent(String tableName) {
        String Service_before = Model.Service_before;
        String Service_end = Model.Service_end;
        String Service_logicDelete = Model.Service_logicDelete;
        Service_before = Service_before.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        Service_before = Service_before.replaceAll("ENTITYNAMEXXX", initcap(lineToHump(tableName)));
        Service_before = Service_before.replaceAll("TIMECDNXX", getNowTime());


        //        查找是否有逻辑删除的字段，（注释中含有logic）
        Service_before = logicDelete(Service_before, Service_logicDelete, "logic") + Service_end;

        return Service_before;
    }


    /**
     * desc: 组织 mapper
     * param:
     * return:
     * author: CDN
     * date: 2019/9/10
     */
    private String getMapperContent(String tableName, String[] colnames, String[] colTypes) {
        String MAPPER_before = Model.MAPPER_before;
        String Mapper_logicDelete = Model.MAPPER_LogicDelete;
        String Mapper_logicEnd = Model.MAPPER_END;
        MAPPER_before = MAPPER_before.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        MAPPER_before = MAPPER_before.replaceAll("ENTITYNAMEXXX", initcap(lineToHump(tableName)));
        MAPPER_before = MAPPER_before.replaceAll("TIMECDNXX", getNowTime());
        //        查找是否有逻辑删除的字段，（注释中含有logic）
        MAPPER_before = logicDelete(MAPPER_before, Mapper_logicDelete, "logic") + Mapper_logicEnd;

        return MAPPER_before;
    }

    /**
     * desc: 判断是否有逻辑删除字段
     * param:
     * return:
     * author: CDN
     * date: 2019/9/21
     */
    public String logicDelete(String yuan, String newContent, String keyword) {
        //        查找是否有逻辑删除的字段，（注释中含有logic）
        for (int i = 0; i < colnames.length; i++) {
            if (colComments.get(i).contains(keyword)) {
                yuan = yuan + newContent;
            }
        }
        return yuan;
    }


    /**
     * // 组织Provider
     *
     * @param tableName 表名
     * @return
     */
    private String getProviderContent(String tableName, String primaryKeyName, String primaryKeyType) {
        String providerCommonPart = Model.PROVIDER_CommonPart; //公共部分

        //新增------------------------------------------------
        String PROVIDER_Insert = "";
        String PROVIDER_Insert_before = Model.PROVIDER_Insert_before;
        String PROVIDER_Insert_condition = Model.PROVIDER_Insert_condition;
        String PROVIDER_Insert_end = Model.PROVIDER_Insert_end;


        String PROVIDER_Delete = Model.PROVIDER_Delete; //删除
        String PROVIDER_logicDelete = Model.PROVIDER_logicDelete; //逻辑删除


//        --------------------------------------------------------
        String PROVIDER_Update = Model.PROVIDER_Update; //更新

//查询---------------------------------------------------
        String PROVIDER_Find = Model.PROVIDER_Find;
        String PROVIDER_Find_status = Model.PROVIDER_Find_status;
        String PROVIDER_Find_end = Model.PROVIDER_Find_end;
        String PROVIDER_FindSingle = Model.PROVIDER_FindSingle;
        String PROVIDER_LIKE = Model.PROVIDER_LIKE; //模糊查询sql语句
        String PROVIDER_CommonMethods = Model.PROVIDER_CommonMethods; //其他方法


//        拼接插入insert
        for (int i = 0; i < colnames.length; i++) {
//                当数据库字段是int系列的时候，通常自增长主键
            if (primaryKeyName.equals(colnames[i])
                    && (primaryKeyType.equalsIgnoreCase("int")
                    || primaryKeyType.equalsIgnoreCase("INT UNSIGNED")
                    || primaryKeyType.equalsIgnoreCase("bigint")
                    || primaryKeyType.equalsIgnoreCase("BIGINT UNSIGNED"))) {
                continue;
            }

            String PROVIDER_Insert_condition_one = PROVIDER_Insert_condition.replaceAll("@Attribute@", lineToHump(colnames[i]));
            String PROVIDER_Insert_condition_two = PROVIDER_Insert_condition_one.replaceAll("XXXfieldXXX", colnames[i]);

            PROVIDER_Insert_before = PROVIDER_Insert_before + PROVIDER_Insert_condition_two;
            if (i == colnames.length - 1) {
                PROVIDER_Insert = PROVIDER_Insert_before + PROVIDER_Insert_end;
            }
        }


        String entityName = initcap(lineToHump(tableName));//实体类名称
        providerCommonPart = providerCommonPart.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        providerCommonPart = providerCommonPart.replaceAll("tableNameXXX", tableName);
        providerCommonPart = providerCommonPart.replaceAll("DepartmentXX", entityName);


//        根据主键更新
        PROVIDER_Update = PROVIDER_Update.replaceAll("DepartmentXX", entityName);
        PROVIDER_Update = PROVIDER_Update.replaceAll("department@@", lineToHump(tableName));
        PROVIDER_Update = PROVIDER_Update.replaceAll("PRYKEY", primaryKeyName);
        PROVIDER_Update = PROVIDER_Update.replaceAll("@Attribute@", lineToHump(primaryKeyName));


//        查询
        int startINdex = PROVIDER_Find.indexOf("START##");
        int endIndex = PROVIDER_Find.indexOf("END##");
        StringBuilder before = new StringBuilder(PROVIDER_Find.substring(0, startINdex));
        String middle = PROVIDER_Find.substring(startINdex, endIndex);
        String bottom = PROVIDER_Find.substring(endIndex, PROVIDER_Find.length());
        for (int i = 0; i < colnames.length; i++) {

//            如果本字段是逻辑删除字段，在查询条件参数就不用该条，下边会判断直接写死状态
            if (colComments.get(i).contains("logic")) {
                continue;
            }

//            如果注释中包含like就拼接为模糊查询，否则就是 = 查询
            if (colComments.get(i).contains("like")) {
                String like2 = PROVIDER_LIKE.replaceAll("XXXXX", lineToHump(colnames[i]));
                String like3 = like2.replaceAll("TTTT", colnames[i]);
                before.append(like3);
            } else {
                String middle2 = middle.replaceAll("XXXXX", lineToHump(colnames[i]));
                String middle3 = middle2.replaceAll("TTTT", colnames[i]);
                before.append(middle3);
            }
        }

//        find 中判断是否有逻辑删除字段
        String tempBefore = logicDelete(before.toString(), PROVIDER_Find_status, "logic");
        if (!before.equals(tempBefore)) {
            before.delete(0, before.length());
            before.append(tempBefore);
        }
//      find 拼接尾部
        before.append(PROVIDER_Find_end);


//        根据主键查询，[查询单条]
        PROVIDER_FindSingle = PROVIDER_FindSingle.replaceAll("KEY", lineToHump(primaryKeyName));
        PROVIDER_FindSingle = PROVIDER_FindSingle.replaceAll("##attribute##", primaryKeyName);
//        }
        String resp = providerCommonPart + PROVIDER_Insert + PROVIDER_Delete;

//        查找是否有逻辑删除的字段，（注释中含有logic）
        for (int i = 0; i < colnames.length; i++) {
            if (colComments.get(i).contains("primaryKey")) {
                PROVIDER_logicDelete = PROVIDER_logicDelete.replaceAll("PRYKEY", colnames[i]);
                PROVIDER_logicDelete = PROVIDER_logicDelete.replaceAll("@Attribute@", lineToHump(colnames[i]));
            } else if (colComments.get(i).contains("logic")) {
                PROVIDER_logicDelete = PROVIDER_logicDelete.replaceAll("STATUSXXX", colnames[i]);
                resp = resp + PROVIDER_logicDelete;
            }
        }

//        如果provider中要加新的方法，在此拼接

        resp = resp + PROVIDER_Update;
        resp = resp + (before.append(bottom).toString()).replaceAll("START##      ", "");
        resp = resp + PROVIDER_FindSingle;
        resp = resp.replaceAll("END##", "");
        resp = resp.replaceAll("TIMECDNXX", getNowTime());
        return resp;
    }


    /**
     * desc:  写入实体
     * param:
     * return:
     * author: CDN
     * date: 2019/9/9
     */

    private boolean startWriteEntity(String outputPath, String content) {
        try (
                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw)) {
            pw.println(content);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取表中字段的所有注释
     *
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String tableName) {
        String SQL = "SELECT * FROM ";// 数据库操作
        //与数据库的连接
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, NAME, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return columnComments;
    }


    /**
     * 功能：生成所有属性
     *
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb, String tablename) {
        List<String> columnCommentsList = getColumnComments(tablename);
//        生成序列号
        sb.append("\n" + genSerialID() + "\r\n\n");
        for (int i = 0; i < colnames.length; i++) {
            if (null != columnCommentsList.get(i) && columnCommentsList.get(i).length() > 0) {
//                注释
                String columnComments = columnCommentsList.get(i);

//                排除注释标记
                if (columnComments.contains("primaryKey")) {
                    columnComments = columnComments.replaceAll("primaryKey", "");
                }
                if (columnComments.contains("logic")) {
                    columnComments = columnComments.replaceAll("logic", "");
                }
                if (columnComments.contains("like")) {
                    columnComments = columnComments.replaceAll("like", "");
                }

//                避免出现空注释
                if (columnComments.length() > 0) {
                    sb.append("\t/**\n\t*" + columnComments + "\n\t*/\n");
                }
            }

//            属性
            sb.append("\t@JsonProperty(\"" + lineToHump(colnames[i]) + "\")\n\tprivate " + sqlType2JavaType(colTypes[i]) + " " + lineToHump(colnames[i]) + ";\r\n\n");
        }

    }

    /**
     * 功能：生成所有方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {

        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tpublic void set" + initcap(lineToHump(colnames[i])) + "(" + sqlType2JavaType(colTypes[i]) + " "
                    + lineToHump(colnames[i]) + "){\r\n");
            sb.append("\tthis." + lineToHump(colnames[i]) + "=" + lineToHump(colnames[i]) + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(lineToHump(colnames[i])) + "(){\r\n");
            sb.append("\t\treturn " + lineToHump(colnames[i]) + ";\r\n");
            sb.append("\t}\r\n");
        }

    }

    /**
     * 下划线转驼峰
     */


    public static String lineToHump(String str) {
        str = str.toLowerCase();
        final StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("_(\\w)");
        Matcher m = p.matcher(str);
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    //生成实体序列号
    public static String genSerialID() {
        return "\tprivate static final long serialVersionUID =  " + Math.abs(new Random().nextLong()) + "L;";
    }


    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }


    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint") || sqlType.equalsIgnoreCase("tinyINT UNSIGNED")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("INT UNSIGNED")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint") || sqlType.equalsIgnoreCase("BIGINT UNSIGNED")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("DOUBLE") || sqlType.equalsIgnoreCase("double")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("decimal")) {
            return "BigDecimal";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        } else if (sqlType.equalsIgnoreCase("TIMESTAMP")) {
            return "Date";
        } else {
            return "Object";
        }
    }

    /**
     * 功能：生成实体类主体代码
     */
    private String createEntity(String tablename) {
        StringBuffer sb = new StringBuffer();
        // 生成package包路径
        sb.append("package " + this.mainPackageOutPath + ".entity" + ";\r\n");
        // 判断是否导入工具包
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n");
        }
        if (f_lang) {
            sb.append("import java.lang.*;\r\n");
        }
        sb.append("import java.io.Serializable;\r\n");
        sb.append("import com.fasterxml.jackson.annotation.JsonProperty;\r\n");
        sb.append("\r\n");
        // 注释部分
        sb.append("   /**\r\n");
        sb.append("    * @ time：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n");
        sb.append("    * @ author：" + this.authorName + " \r\n");
        sb.append("    * @ desc：" + tablename + " 实体类\r\n");
        sb.append("    */ \r\n");
        // 实体部分
        sb.append("\r\npublic class " + initcap(lineToHump(tablename)) + " implements Serializable" + "{\r\n");
        processAllAttrs(sb, tablename);// 属性
        processAllMethod(sb);// get set方法
        sb.append("}\r\n");

        return sb.toString();
    }

    /**
     * desc: 获取当前日期
     * param:
     * return:
     * author: CDN
     * date: 2019/9/14
     */
    public String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 出口 TODO
     *
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        new SqlHelper("localhost/spring", "root", "root", "com.springboot.demo");

    }


}
