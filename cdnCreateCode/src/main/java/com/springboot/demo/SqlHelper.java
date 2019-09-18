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
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));





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
     * 构造函数
     */
    public SqlHelper(String dataBaseUrl, String dataBaseUserName, String dataBasePwd, String packagePath) {
        URL = "jdbc:mysql://" + dataBaseUrl + "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
        NAME = dataBaseUserName;
        PASS = dataBasePwd;
        mainPackageOutPath = packagePath;

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

        CreateFileUtil.createDir(controllerPath);
        CreateFileUtil.createDir(entityPath);
        CreateFileUtil.createDir(mapperPath);
        CreateFileUtil.createDir(providerPath);
        CreateFileUtil.createDir(servicePath);
        CreateFileUtil.createDir(serviceImplPath);
        CreateFileUtil.createDir(utilsPath);
        CreateFileUtil.createDir(mybatisPath);

//        生成工具类，不用循环
        generateUtils(utilsPath + "/");

        getTableNames();
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

//
//                String path = this.getClass().getResource("").getPath();
//                System.out.println(path);
                count++;
                boolean primaryKeyExist = false;
                String primaryKeyName = "";
                String primaryKeyType = "";
                for (int i = 0; i < colnames.length; i++) {
                    if (colComments.get(i).contains("primaryKey")) {
                        primaryKeyName = colnames[i];
                        primaryKeyType = colTypes[i];
                        primaryKeyExist = true;
                    }
                }

//                如果存在主键标识
                if (primaryKeyExist) {
                    String entityContent = createEntity(tablename);
                    String mapperContent = getMapperContent(tablename, colnames, colTypes);
                    String providerContent = getProviderContent(tablename);
                    String serviceContent = getServiceContent(tablename);
                    String serviceImplContent = getServiceImpl(tablename, primaryKeyName, primaryKeyType);
                    String controllerContent = getControllerContent(tablename, primaryKeyName, primaryKeyType);

//                    -----------------正式生成-------------------------
                    startWriteEntity(entityFinalPath, entityContent); //生成实体
                    CreateFileUtil.createFile(mapperFinalPath, mapperContent); //生成mapper
                    CreateFileUtil.createFile(providerFinalPath, providerContent); //生成privoder
                    CreateFileUtil.createFile(serviceFinalPath, serviceContent); //生成service
                    CreateFileUtil.createFile(serviceImplFinalPath, serviceImplContent); //生成serviceImpl
                    CreateFileUtil.createFile(controllerFinalPath, controllerContent); //生成controller


                    System.out.println("第" + count + "个表生成完毕，生成路径为：" + entityFinalPath);
                } else {
                    System.out.println("\n\t>>>>>>>>>>\n\t在主键注释中加“primaryKey”\n\t<<<<<<<<<<");
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
        String myException = getMyException();
        map.put("JsonResult.java", JsonResultContent);
        map.put("MyException.java", myException);
        map.put("StrUtils.java", StrUtilsContent);

        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            CreateFileUtil.createFile(utilsFinalPath + stringStringEntry.getKey(), stringStringEntry.getValue());
        }

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
     * desc:
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
        String controllerContent = Model.Controller;
        controllerContent = controllerContent.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        controllerContent = controllerContent.replaceAll("ENTITYNAMEXXX", initcap(lineToHump(tableName)));
        controllerContent = controllerContent.replaceAll("TABLENAME_CDN", lineToHump(tableName));
        controllerContent = controllerContent.replaceAll("TIMECDNXX", getNowTime());
        return controllerContent;
    }

    /**
     * desc: 组织serviceImpl
     * param:
     * return:
     * author: CDN
     * date: 2019/9/14
     */
    private String getServiceImpl(String tableName, String primaryKeyName, String primaryKeyType) {
        String serviceImpl = Model.ServiceImpl;
        serviceImpl = serviceImpl.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        serviceImpl = serviceImpl.replaceAll("ENTITYNAMEXXX", initcap(lineToHump(tableName)));
        serviceImpl = serviceImpl.replaceAll("##XXXX##", lineToHump(tableName));
        serviceImpl = serviceImpl.replaceAll("primaryKeyXX", lineToHump(primaryKeyName));
        serviceImpl = serviceImpl.replaceAll("PrimaryKeyXX", initcap(lineToHump(primaryKeyName)));
        if (primaryKeyName != null && !"".equals(primaryKeyName) && "VARCHAR".equals(primaryKeyType)) {
            String temp = "obj.setXVXVXVX(StrUtils.generateUUID());";
            temp = temp.replaceAll("XVXVXVX", initcap(lineToHump(primaryKeyName)));
            serviceImpl = serviceImpl.replaceAll("XVXVXVX", temp);
        } else {
            serviceImpl = serviceImpl.replaceAll("XVXVXVX", "");
        }
        serviceImpl = serviceImpl.replaceAll("TIMECDNXX", getNowTime());

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
        String Service_common = Model.Service_common;
        Service_common = Service_common.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        Service_common = Service_common.replaceAll("ENTITYNAMEXXX", initcap(lineToHump(tableName)));
        Service_common = Service_common.replaceAll("TIMECDNXX", getNowTime());
        return Service_common;
    }


    /**
     * desc: 组织 mapper
     * param:
     * return:
     * author: CDN
     * date: 2019/9/10
     */
    private String getMapperContent(String tableName, String[] colnames, String[] colTypes) {
        String mapperMode = Model.MAPPER_CommonPart;
        mapperMode = mapperMode.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        mapperMode = mapperMode.replaceAll("ENTITYNAMEXXX", initcap(lineToHump(tableName)));
        mapperMode = mapperMode.replaceAll("TIMECDNXX", getNowTime());
        return mapperMode;
    }

    /**
     * // 组织Provider
     *
     * @param tableName 表名
     * @return
     */
    private String getProviderContent(String tableName) {
        String providerCommonPart = Model.PROVIDER_CommonPart; //公共部分
        String PROVIDER_Insert = Model.PROVIDER_Insert; //新增
        String PROVIDER_Delete = Model.PROVIDER_Delete; //删除
        String PROVIDER_Update = Model.PROVIDER_Update; //更新
        String PROVIDER_Find = Model.PROVIDER_Find; //查询
        String PROVIDER_FindSingle = Model.PROVIDER_FindSingle; //查询单条
        String PROVIDER_LIKE = Model.PROVIDER_LIKE; //模糊查询sql语句
        String PROVIDER_CommonMethods = Model.PROVIDER_CommonMethods; //公共方法
        boolean isPrimaryKey = false;
        String entityName = initcap(lineToHump(tableName));//实体类名称
        providerCommonPart = providerCommonPart.replaceAll("com.springboot.demo", this.mainPackageOutPath);
        providerCommonPart = providerCommonPart.replaceAll("tableNameXXX", tableName);
        providerCommonPart = providerCommonPart.replaceAll("DepartmentXX", entityName);

        PROVIDER_Insert = PROVIDER_Insert.replaceAll("DepartmentXX", entityName);
        PROVIDER_Insert = PROVIDER_Insert.replaceAll("department@@", lineToHump(tableName));

//        根据主键更新
        PROVIDER_Update = PROVIDER_Update.replaceAll("DepartmentXX", entityName);
        PROVIDER_Update = PROVIDER_Update.replaceAll("department@@", lineToHump(tableName));
        for (int i = 0; i < colnames.length; i++) {
            if (colComments.get(i).contains("primaryKey")) {
                PROVIDER_Update = PROVIDER_Update.replaceAll("PRYKEY", colnames[i]);
                PROVIDER_Update = PROVIDER_Update.replaceAll("@Attribute@", lineToHump(colnames[i]));
                isPrimaryKey = true;
            }
        }


//        查询
        int startINdex = PROVIDER_Find.indexOf("START##");
        int endIndex = PROVIDER_Find.indexOf("END##");
        StringBuilder before = new StringBuilder(PROVIDER_Find.substring(0, startINdex));
        String middle = PROVIDER_Find.substring(startINdex, endIndex);
        String bottom = PROVIDER_Find.substring(endIndex, PROVIDER_Find.length());
        for (int i = 0; i < colnames.length; i++) {
            if ("VARCHAR".equals(colTypes[i])) {
                String like2 = PROVIDER_LIKE.replaceAll("XXXXX", lineToHump(colnames[i]));
                String like3 = like2.replaceAll("TTTT", colnames[i]);
                before.append(like3);
            } else {
                String middle2 = middle.replaceAll("XXXXX", lineToHump(colnames[i]));
                String middle3 = middle2.replaceAll("TTTT", colnames[i]);
                before.append(middle3);
            }
        }

//        根据主键查询，在主键注释中加 primaryKey
        for (int i = 0; i < colnames.length; i++) {
            if (colComments.get(i).contains("primaryKey")) {
                PROVIDER_FindSingle = PROVIDER_FindSingle.replaceAll("KEY", lineToHump(colnames[i]));
                PROVIDER_FindSingle = PROVIDER_FindSingle.replaceAll("##attribute##", colnames[i]);
                isPrimaryKey = true;
            }
        }
        String resp = providerCommonPart + PROVIDER_Insert + PROVIDER_Delete;
        if (isPrimaryKey) {
            resp = resp + PROVIDER_Update;
        }
        resp = resp + (before.append(bottom).toString()).replaceAll("START##      ", "");
        if (isPrimaryKey) {
            resp = resp + PROVIDER_FindSingle;
        }
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
        List<String> columnComments = getColumnComments(tablename);
        for (int i = 0; i < colnames.length; i++) {
            if (null != columnComments.get(i) || !"".equals(columnComments.get(i))) {
//                注释
                sb.append("\t/**\n\t*" + columnComments.get(i) + "\n\t*/\n");
            }
//            属性
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + lineToHump(colnames[i]) + ";\r\n\n");
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
                || sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("DOUBLE")) {
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
            System.out.println("数据类型异常，类型为：" + sqlType);
        }

        return null;
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
        sb.append("\r\n");
        // 注释部分
        sb.append("   /**\r\n");
        sb.append("    * @ time：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n");
        sb.append("    * @ authorName：" + this.authorName + " \r\n");
        sb.append("    * @ desc：" + tablename + " 实体类\r\n");
        sb.append("    */ \r\n");
        // 实体部分
        sb.append("\r\n\r\npublic class " + initcap(lineToHump(tablename)) + "{\r\n");
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
    public static void main(String[] args) {
        new SqlHelper("localhost/caidingnu", "root", "root", "com.springboot.demo");
    }

}
