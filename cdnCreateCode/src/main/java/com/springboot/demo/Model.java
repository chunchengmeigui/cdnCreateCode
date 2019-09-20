package com.springboot.demo;


/**
 * desc：
 * author CDN
 * create 2019-09-09 23:19
 * version 1.0.0
 */
public class Model {

    //   ################################################ provider ####################################################
    static final String PROVIDER_CommonPart = "package com.springboot.demo.mapper.provider;\n" +
            "\n" +
            "import com.springboot.demo.entity.DepartmentXX;\n" +
            "import org.apache.ibatis.jdbc.SQL;\n" +
            "import java.lang.reflect.Field;\n" +
            "import com.springboot.demo.utils.StrUtils;\n" +
            "import java.lang.reflect.Array;\n" +
            "import java.util.Collection;\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * desc：\n" +
            " * author CDN\n" +
            " * create TIMECDNXX\n" +
            " * version 1.0.0\n" +
            " */\n" +
            "public class DepartmentXXProvider {\n" +
            "\n" +
            "    private final static String tableName = \"tableNameXXX\";";

    static final String PROVIDER_Insert = "\n\n      /**\n" +
            "     * desc: 新增\n" +
            "     * param:\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    public String insert(DepartmentXX department@@) {\n" +
            "        SQL sql = new SQL();\n" +
            "        if (!StrUtils.isNullOrEmpty(department@@)) {\n" +
            "            sql.INSERT_INTO(tableName);\n" +
            "            for (Field field : department@@.getClass().getDeclaredFields()) {\n" +
            "                sql.VALUES(StrUtils.camel2Underline(field.getName()), \"#{\" + field.getName() + \"}\");\n" +
            "            }\n" +
            "        }\n" +
            "        return sql.toString();\n" +
            "    }";

    static final String PROVIDER_Delete = "\n\n  /**\n" +
            "     * desc:条件删除\n" +
            "     * param:\n" +
            "     * return:\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    public  String deleteByCondition(Map<String, Object> map) {\n" +
            "        SQL sql = new SQL();\n" +
            "        if (!StrUtils.isNullOrEmpty(map)) {\n" +
            "            sql.DELETE_FROM(tableName);\n" +
            "            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {\n" +
            "                sql.WHERE(stringObjectEntry.getKey()+\"=\"+stringObjectEntry.getValue());\n" +
            "            }\n" +
            "        }\n" +
            "        return sql.toString();\n" +
            "    }";

    static final String PROVIDER_logicDelete = "\n" +
            "\n" +
            "    /**\n" +
            "     * desc: 逻辑删除\n" +
            "     * param: 2 删除  1 正常\n" +
            "     * return:\n" +
            "     * author: CDN\n" +
            "     * date: 2019/9/20\n" +
            "     */\n" +
            "    public String logicDelete(Map<String, Object> map) {\n" +
            "        SQL sql = new SQL();\n" +
            "        if (!StrUtils.isNullOrEmpty(map)){\n" +
            "            sql.UPDATE(tableName).SET(\"STATUSXXX =2 \").WHERE(\"PRYKEY=#{@Attribute@}\");\n" +
            "        }\n" +
            "        return sql.toString();\n" +
            "    }\n" +
            "\n";

    static final String PROVIDER_Update = "\n\n  /**\n" +
            "     * desc: 根据主键更新\n" +
            "     * param:\n" +
            "     * return:\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    public String update(DepartmentXX department@@) {\n" +
            "        SQL sql = new SQL();\n" +
            "        if (!StrUtils.isNullOrEmpty(department@@)) {\n" +
            "            sql.UPDATE(tableName).WHERE(\"PRYKEY=#{@Attribute@}\");\n" +
            "            for (Field field : department@@.getClass().getDeclaredFields()) {\n" +
            "                if(\"@Attribute@\".equals(field.getName())){\n" +
            "                     continue ;\n" +
            "                 }\n" +
            "                sql.SET(StrUtils.camel2Underline(field.getName()) + \"=#{\" + field.getName() + \"}\");\n" +
            "            }\n" +
            "        }\n" +
            "        return sql.toString();\n" +
            "    }";


    static final String PROVIDER_Find = "\n\n   /**\n" +
            "     * desc: 查询\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    public  String find(Map<String, Object> map) {\n" +
            "        SQL sql = new SQL().SELECT(\"*\").FROM(tableName);\n" +
            "        if (map != null) {\n" +
            "         START##" +
            "        if (!StrUtils.isNullOrEmpty(map.get(\"XXXXX\"))){\n" +
            "            sql.WHERE(\"TTTT=#{XXXXX}\");\n\t" +
            "        }\n" +
            "         END##" +
            "    }\n";

    static final String PROVIDER_LIKE = "if (!StrUtils.isNullOrEmpty(map.get(\"XXXXX\"))){\n" +
            "                sql.WHERE(\"TTTT like concat('%',#{XXXXX},'%')\");\n" +
            "            }\n\t\t";

    static final String PROVIDER_Find_status = " sql.WHERE(\"status=1\");\n";

    static final String PROVIDER_Find_end = "        }\n        return sql.toString();\n";

    static final String PROVIDER_FindSingle = "\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * desc: 查询单条记录\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    public String findSingle(Map<String, Object> map) {\n" +
            "        SQL sql = new SQL().SELECT(\"*\").FROM(tableName);\n" +
            "        if (!StrUtils.isNullOrEmpty(map)) {\n" +
            "            sql.WHERE(\"KEY=#{##attribute##}\");\n" +
            "        }\n" +
            "        return sql.toString();\n" +
            "    }\n" +
            "\n" +
            "    " +
            "\n}";


    static final String PROVIDER_CommonMethods = "\n" +
            "\n" +
            "    /**\n" +
            "     * 判断对象为空字符串或者为null，如果满足其中一个条件，则返回true\n" +
            "     *\n" +
            "     * @return\n" +
            "     */\n" +
            "    public boolean isNullOrEmpty(Object obj) {\n" +
            "        boolean isEmpty = false;\n" +
            "        if (obj == null) {\n" +
            "            isEmpty = true;\n" +
            "        } else if (obj instanceof String) {\n" +
            "            isEmpty = ((String) obj).trim().isEmpty();\n" +
            "        } else if (obj instanceof Collection) {\n" +
            "            isEmpty = (((Collection) obj).size() == 0);\n" +
            "        } else if (obj instanceof Map) {\n" +
            "            isEmpty = ((Map) obj).size() == 0;\n" +
            "        } else if (obj.getClass().isArray()) {\n" +
            "            isEmpty = Array.getLength(obj) == 0;\n" +
            "        } else if (obj instanceof Long) {\n" +
            "            isEmpty = Long.parseLong(obj + \"\") < 1;\n" +
            "        }\n" +
            "        return isEmpty;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * 驼峰转下划线\n" +
            "     *\n" +
            "     * @param camelCaseName\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static String camel2Underline(String camelCaseName) {\n" +
            "        StringBuilder result = new StringBuilder();\n" +
            "        if (camelCaseName != null && camelCaseName.length() > 0) {\n" +
            "            result.append(camelCaseName.substring(0, 1).toLowerCase());\n" +
            "            for (int i = 1; i < camelCaseName.length(); i++) {\n" +
            "                char ch = camelCaseName.charAt(i);\n" +
            "                if (Character.isUpperCase(ch)) {\n" +
            "                    result.append(\"_\");\n" +
            "                    result.append(Character.toLowerCase(ch));\n" +
            "                } else {\n" +
            "                    result.append(ch);\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        return result.toString();\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "}";


//    #########################################  mapper  ###############################################

    public static final String MAPPER_before = "package com.springboot.demo.mapper;\n" +
            "\n" +
            "\n" +
            "import com.springboot.demo.entity.ENTITYNAMEXXX;\n" +
            "import org.apache.ibatis.annotations.DeleteProvider;\n" +
            "import org.apache.ibatis.annotations.InsertProvider;\n" +
            "import org.apache.ibatis.annotations.SelectProvider;\n" +
            "import com.springboot.demo.mapper.provider.ENTITYNAMEXXXProvider;\n" +
            "import org.apache.ibatis.annotations.UpdateProvider;\n" +
            "\n" +
            "import java.util.HashMap;\n" +
            "import java.util.List;\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * desc：\n" +
            " * author CDN\n" +
            " * create TIMECDNXX\n" +
            " * version 1.0.0\n" +
            " */\n" +
            "public interface ENTITYNAMEXXXMapper {\n" +
            "\n" +
            "    @InsertProvider(type = ENTITYNAMEXXXProvider.class, method = \"insert\")\n" +
            "    int insert(ENTITYNAMEXXX obj);\n" +
            "\n" +
            "    @DeleteProvider(type = ENTITYNAMEXXXProvider.class, method = \"deleteByCondition\")\n" +
            "    int deleteByCondition(Map<String, Object> map);\n" +
            "\n" +
            "    @UpdateProvider(type = ENTITYNAMEXXXProvider.class, method = \"update\")\n" +
            "    int update(ENTITYNAMEXXX obj);\n" +
            "\n" +
            "    @SelectProvider(type = ENTITYNAMEXXXProvider.class, method = \"find\")\n" +
            "    List<ENTITYNAMEXXX> find(Map<String, Object> map);\n" +
            "\n" +
            "    @SelectProvider(type = ENTITYNAMEXXXProvider.class, method = \"findSingle\")\n" +
            "    ENTITYNAMEXXX findSingle(Map<String, Object> map);\n";

    static final String MAPPER_LogicDelete = "\n" +
            "    @UpdateProvider(type = MenuProvider.class, method = \"logicDelete\")\n" +
            "    int logicDelete(Map<String, Object> map);\n";

    static final String MAPPER_END = "}\n";

//    ########################################### Service #################################################

    static final String Service_before = "package com.springboot.demo.service;\n" +
            "\n" +
            "import com.springboot.demo.entity.ENTITYNAMEXXX;\n" +
            "\n" +
            "import java.util.HashMap;\n" +
            "import com.github.pagehelper.PageInfo;\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * desc:\n" +
            " * author caidingnu\n" +
            " * create TIMECDNXX\n" +
            " * version 1.0.0\n" +
            " */\n" +
            "public interface ENTITYNAMEXXXService {\n" +
            "\n" +
            "    int insert(ENTITYNAMEXXX obj);\n" +
            "\n" +
            "    int deleteByCondition(Map<String, Object> map);\n" +
            "\n" +
            "    int update(ENTITYNAMEXXX obj);\n" +
            "\n" +
            "    PageInfo<ENTITYNAMEXXX> find(Map<String, Object> map);\n" +
            "\n" +
            "    ENTITYNAMEXXX findSingle(Map<String, Object> map);\n" +
            "\n";

    static final String Service_logicDelete = "    int deleteLogic(Map<String, Object> map);\n";

    static final String Service_end = "}\n";

    //    ########################### ServiceImpl  #################################
    static final String ServiceImpl_before = "package com.springboot.demo.service.impl;\n" +
            "\n" +
            "import com.github.pagehelper.PageHelper;\n" +
            "import com.github.pagehelper.PageInfo;\n" +
            "import com.springboot.demo.utils.MyException;\n" +
            "import com.springboot.demo.utils.StrUtils;\n" +
            "import com.springboot.demo.entity.ENTITYNAMEXXX;\n" +
            "import com.springboot.demo.mapper.ENTITYNAMEXXXMapper;\n" +
            "import com.springboot.demo.service.ENTITYNAMEXXXService;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "\n" +
            "import javax.annotation.Resource;\n" +
            "import java.util.HashMap;\n" +
            "import java.util.List;\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * desc：\n" +
            " * author CDN\n" +
            " * create TIMECDNXX\n" +
            " */\n" +
            "@Service\n" +
            "public class ENTITYNAMEXXXServiceImpl implements ENTITYNAMEXXXService {\n" +
            "\n" +
            "    @Resource\n" +
            "    ENTITYNAMEXXXMapper ##XXXX##Mapper;\n" +
            "\n" +
            "    @Override\n" +
            "    public int insert(ENTITYNAMEXXX obj) {\n" +
            "         XVXVXVX" + "\n" +
            "        if (StrUtils.isNullOrEmpty(obj)) {\n" +
            "            throw new MyException(\"插入数据不能为空！\");\n" +
            "        }\n" +
            "        return ##XXXX##Mapper.insert(obj);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int deleteByCondition(Map<String, Object> map) {\n" +
            "        if (\"\".equals(map.get(\"primaryKeyXX\")) || null == map.get(\"primaryKeyXX\")) {\n" +
            "            throw new MyException(\"主键不能为空！\");\n" +
            "        }\n" +
            "        return ##XXXX##Mapper.deleteByCondition(map);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public int update(ENTITYNAMEXXX obj) {\n" +
            "        if (\"\".equals(obj.getPrimaryKeyXX() + \"\") || null == obj.getPrimaryKeyXX()) {\n" +
            "            throw new MyException(\"主键不能为空！\");\n" +
            "        }\n" +
            "        return ##XXXX##Mapper.update(obj);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public  PageInfo<ENTITYNAMEXXX> find(Map<String, Object> map) {\n" +
            "\n" +
            "        int pageIndex = 0;\n" +
            "        int pageSize = 10;\n" +
            "        if (map.get(\"pageIndex\") != null) {\n" +
            "            pageIndex = Integer.parseInt(map.get(\"pageIndex\") + \"\");\n" +
            "        }\n" +
            "        if (map.get(\"pageSize\") != null) {\n" +
            "            pageIndex = Integer.parseInt(map.get(\"pageSize\") + \"\");\n" +
            "        }\n" +
            "        PageHelper.startPage(pageIndex, pageSize);\n" +
            "        List<ENTITYNAMEXXX> list = ##XXXX##Mapper.find(map);\n" +
            "\n" +
            "        return new PageInfo<>(list);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public ENTITYNAMEXXX findSingle(Map<String, Object> map) {\n" +
            "        if (\"\".equals(map.get(\"primaryKeyXX\")) || null == map.get(\"primaryKeyXX\")) {\n" +
            "            throw new MyException(\"主键不能为空！\");\n" +
            "        }\n" +
            "        return ##XXXX##Mapper.findSingle(map);\n" +
            "    }\n";

    static final String ServiceImpl_logicDelete = "\n" +
            "\n" +
            "    @Override\n" +
            "    public int deleteLogic(Map<String, Object> map) {\n" +
            "        if (\"\".equals(map.get(\"menuId\")) || null == map.get(\"primaryKeyXX\")) {\n" +
            "            throw new MyException(\"主键不能为空！\");\n" +
            "        }\n" +
            "        return ##XXXX##Mapper.logicDelete(map);\n" +
            "    }\n";

    static final String ServiceImpl_end = "}\n";


    //    ############################################## Controller ########################################################
    static final String Controller_before = "package com.springboot.demo.controller;\n" +
            "\n" +
            "import com.github.pagehelper.PageInfo;\n" +
            "import com.springboot.demo.utils.JsonResult;\n" +
            "import com.springboot.demo.entity.ENTITYNAMEXXX;\n" +
            "import com.springboot.demo.service.ENTITYNAMEXXXService;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.web.bind.annotation.RequestBody;\n" +
            "import org.springframework.web.bind.annotation.RequestMapping;\n" +
            "import org.springframework.web.bind.annotation.RequestParam;\n" +
            "import org.springframework.web.bind.annotation.RestController;\n" +
            "\n" +
            "import java.util.HashMap;\n" +
            "import java.util.Map;\n" +
            "\n" +
            "/**\n" +
            " * desc：\n" +
            " * author CDN\n" +
            " * create TIMECDNXX\n" +
            " */\n" +
            "@RestController\n" +
            "@RequestMapping(\"TABLENAME_CDN\")\n" +
            "public class ENTITYNAMEXXXController {\n" +
            "\n" +
            "    @Autowired\n" +
            "    ENTITYNAMEXXXService TABLENAME_CDNService;\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * desc: 新增\n" +
            "     * param:\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    @RequestMapping(\"insert\")\n" +
            "    public JsonResult insert(@RequestBody ENTITYNAMEXXX TABLENAME_CDN) {\n" +
            "        return JsonResult.buildSuccess(TABLENAME_CDNService.insert(TABLENAME_CDN));\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * desc: 条件删除\n" +
            "     * param:\n" +
            "     * return:\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    @RequestMapping(\"deleteByCondition\")\n" +
            "    public JsonResult deleteByCondition(@RequestBody Map map) {\n" +
            "        return JsonResult.buildSuccess(TABLENAME_CDNService.deleteByCondition(map));\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * desc: 修改\n" +
            "     * param:\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    @RequestMapping(\"update\")\n" +
            "    public JsonResult update(@RequestBody ENTITYNAMEXXX TABLENAME_CDN) {\n" +
            "        return JsonResult.buildSuccess(TABLENAME_CDNService.update(TABLENAME_CDN));\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * desc: 条件查询(分页)\n" +
            "     * param:\n" +
            "     * return:\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    @RequestMapping(\"getPageList\")\n" +
            "    public JsonResult getPageList(@RequestParam HashMap map) {\n" +
            "        PageInfo<ENTITYNAMEXXX> pageInfo = TABLENAME_CDNService.find(map);\n" +
            "        return JsonResult.buildSuccess(pageInfo);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * desc: 单条记录\n" +
            "     * param:\n" +
            "     * return:\n" +
            "     * author: CDN\n" +
            "     * date: TIMECDNXX\n" +
            "     */\n" +
            "    @RequestMapping(\"findSingle\")\n" +
            "    public JsonResult findSingle(@RequestParam Map map) {\n" +
            "        return JsonResult.buildSuccess(TABLENAME_CDNService.findSingle(map));\n" +
            "    }\n";


    static final String Controller_LogicDelete = "\n" +
            "\n" +
            "    /**\n" +
            "     * desc: 逻辑删除\n" +
            "     * param:\n" +
            "     * return:\n" +
            "     * author: CDN\n" +
            "     * date: 2019/9/21\n" +
            "     */\n" +
            "    @RequestMapping(\"deleteLogic\")\n" +
            "    public JsonResult deleteLogic(@RequestBody Map<String, Object> map) {\n" +
            "        return JsonResult.buildSuccess(menuService.deleteLogic(map));\n" +
            "    }\n";

    static final String Controller_End = "}\n";


    //  ########################################### Utils #################################################
    static final String MyException = "package com.springboot.demo.utils;\n" +
            "\n" +
            "public class MyException extends RuntimeException {\n" +
            "\n" +
            "    /**\n" +
            "     * 状态码\n" +
            "     * 99 系统异常\n" +
            "     * 98 业务提示\n" +
            "     * 96 未登录或已超时\n" +
            "     * 00 正常\n" +
            "     */\n" +
            "    private String code;\n" +
            "    /**\n" +
            "     * 异常消息\n" +
            "     */\n" +
            "    private String msg;\n" +
            "\n" +
            "    public MyException(String msg, String code){\n" +
            "        super(msg);\n" +
            "        this.code = code;\n" +
            "        this.msg = msg;\n" +
            "    }\n" +
            "\n" +
            "    public MyException(String msg){\n" +
            "        super(msg);\n" +
            "        this.code = \"98\";\n" +
            "        this.msg = msg;\n" +
            "    }\n" +
            "\n" +
            "    public MyException(String message, Throwable cause){\n" +
            "        super(message, cause);\n" +
            "        this.code = \"98\";\n" +
            "        this.msg = message;\n" +
            "    }\n" +
            "\n" +
            "    public String getCode() {\n" +
            "        return code;\n" +
            "    }\n" +
            "\n" +
            "    public void setCode(String code) {\n" +
            "        this.code = code;\n" +
            "    }\n" +
            "\n" +
            "    public String getMsg() {\n" +
            "        return msg;\n" +
            "    }\n" +
            "\n" +
            "    public void setMsg(String msg) {\n" +
            "        this.msg = msg;\n" +
            "    }\n" +
            "\n" +
            "}\n";

    static final String JsonResult = "package com.springboot.demo.utils;\n" +
            "\n" +
            "\n" +
            "import java.io.Serializable;\n" +
            "\n" +
            "/**\n" +
            " * 功能描述：api统一返回数据模型\n" +
            " *\n" +
            " * <p> 创建时间：3 5, 2019 11:19:06 AM </p>\n" +
            " */\n" +
            "public class JsonResult implements Serializable {\n" +
            "\n" +
            "    /**\n" +
            "     * 状态码 1表示成功，0表示处理中，-1表示失败\n" +
            "     */\n" +
            "    private String code;\n" +
            "    /**\n" +
            "     * 返回数据\n" +
            "     */\n" +
            "    private Object data;\n" +
            "    /**\n" +
            "     * 返回消息\n" +
            "     */\n" +
            "    private String msg;\n" +
            "\n" +
            "    public JsonResult() {\n" +
            "    }\n" +
            "\n" +
            "    public JsonResult(String code, Object data, String msg) {\n" +
            "        this.code = code;\n" +
            "        this.data = data;\n" +
            "        this.msg = msg;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 成功00/执行中-1/01-99 失败  返回[自定义消息]，[自定义状态码]，[自定义数据]\n" +
            "     * 如果msg为空，msg=“成功”，如果code为空，code=1\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult build(String msg, String code, Object data) throws MyException {\n" +
            "        if(msg==null||msg.trim().length()<1) throw  new MyException(\"消息为空\");\n" +
            "        if(code==null) throw  new MyException(\"code为空\");\n" +
            "        return new JsonResult(code, data, msg);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 成功  返回【自定义数据】，msg=“成功”，code=00\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildSuccess(Object data) {\n" +
            "        return new JsonResult(\"00\", data, \"成功\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 成功  返回【自定义消息】，data=null，code=1\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildSuccess(String msg) {\n" +
            "        return new JsonResult(\"00\", null, msg);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 成功  返回【自定义数据】，【自定义msg】，code=1\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildSuccess(Object data, String msg) throws MyException {\n" +
            "        if(msg==null||msg.trim().length()<1) throw  new MyException(\"消息为空\");\n" +
            "        return new JsonResult(\"00\", data, msg);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 失败  返回【自定义数据】，msg=“失败”，code=98\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildError(Object data) {\n" +
            "        return new JsonResult(\"99\", data, \"失败\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 失败  返回【自定义消息】，data=null，code=99\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildError(String msg) {\n" +
            "        return new JsonResult(\"99\", null, msg);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 失败  返回【自定义数据】，【自定义msg】，code=0\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildError(Object data, String msg) throws Exception {\n" +
            "        if(msg==null||msg.trim().length()<1) throw  new Exception(\"消息为空\");\n" +
            "        return new JsonResult(\"99\", data, msg);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 业务失败  返回【自定义数据】，【自定义msg】，code=0\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildFail(String msg) {\n" +
            "        return new JsonResult(\"98\", null, msg);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 执行中  返回【自定义数据】，msg=“执行中...”，code=-1\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildWorking(Object data) {\n" +
            "        return new JsonResult(\"-1\", data, \"执行中...\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 执行中  返回【自定义消息】，data=null，code=0\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildWorking(String msg) {\n" +
            "        return new JsonResult(\"-1\", null, msg);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 执行中  返回【自定义数据】，【自定义msg】，code=0\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static JsonResult buildWorking(Object data, String msg) throws Exception {\n" +
            "        if(msg==null||msg.trim().length()<1) throw  new Exception(\"消息为空\");\n" +
            "        return new JsonResult(\"-1\", data, msg);\n" +
            "    }\n" +
            "\n" +
            "    public String getCode() {\n" +
            "        return code;\n" +
            "    }\n" +
            "\n" +
            "    public void setCode(String code) {\n" +
            "        this.code = code;\n" +
            "    }\n" +
            "\n" +
            "    public Object getData() {\n" +
            "        return data;\n" +
            "    }\n" +
            "\n" +
            "    public void setData(Object data) {\n" +
            "        this.data = data;\n" +
            "    }\n" +
            "\n" +
            "    public String getMsg() {\n" +
            "        return msg;\n" +
            "    }\n" +
            "\n" +
            "    public void setMsg(String msg) {\n" +
            "        this.msg = msg;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 结果返回字符串\n" +
            "     * @return\n" +
            "     */\n" +
            "    @Override\n" +
            "    public String toString() {\n" +
            "        return \"JsonResult [code=\" + code + \", data=\" + data + \", msg=\" + msg + \"]\";\n" +
            "    }\n" +
            "\n" +
            "}\n";

    static final String StrUtil = "package com.springboot.demo.utils;\n" +
            "\n" +
            "import java.lang.reflect.Array;\n" +
            "import java.util.*;\n" +
            "\n" +
            "/**\n" +
            " * 字符串工具类\n" +
            " */\n" +
            "public class StrUtils {\n" +
            "\n" +
            "    public static Boolean isEmpty(Object type) {\n" +
            "        if (type != null && !\"\".equals(type)) {\n" +
            "            return false;\n" +
            "        }\n" +
            "        return true;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 拿到后缀名\n" +
            "     *\n" +
            "     * @param fulleName\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static String getSuffix(String fulleName) {\n" +
            "        if (isNullOrEmpty(fulleName)) return  null;\n" +
            "        return fulleName.substring(fulleName.lastIndexOf(\".\"),fulleName.length()-1);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 判断字符串为空字符串或者为null，如果满足其中一个条件，则返回true\n" +
            "     *\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean isNullOrEmpty(String str) {\n" +
            "        Object obj = (Object) str;\n" +
            "        return isNullOrEmpty(obj);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 判断对象为空字符串或者为null，如果满足其中一个条件，则返回true\n" +
            "     *\n" +
            "     * @return\n" +
            "     */\n" +
            "    @SuppressWarnings(\"rawtypes\")\n" +
            "    public static boolean isNullOrEmpty(Object obj) {\n" +
            "        boolean isEmpty = false;\n" +
            "        if (obj == null) {\n" +
            "            isEmpty = true;\n" +
            "        } else if (obj instanceof String) {\n" +
            "            isEmpty = ((String) obj).trim().isEmpty();\n" +
            "        } else if (obj instanceof Collection) {\n" +
            "            isEmpty = (((Collection) obj).size() == 0);\n" +
            "        } else if (obj instanceof Map) {\n" +
            "            isEmpty = ((Map) obj).size() == 0;\n" +
            "        } else if (obj.getClass().isArray()) {\n" +
            "            isEmpty = Array.getLength(obj) == 0;\n" +
            "        }else if (obj instanceof  Long){\n" +
            "            isEmpty=Long.parseLong(obj+\"\")<1;\n" +
            "        }\n" +
            "        return isEmpty;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * 检查 email输入是否正确 正确的书写格 式为 username@domain\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkEmail(String text, int length) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"\\\\w+([-+.]\\\\w+)*@\\\\w+([-.]\\\\w+)*\\\\.\\\\w+([-.]\\\\w+)*\") && text.length() <= length;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查电话输入 是否正确 正确格 式 012-87654321、0123-87654321、0123－7654321\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkTelephone(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\n" +
            "                \"(0\\\\d{2,3}-\\\\d{7,8})|\" +\n" +
            "                        \"(0\\\\d{9,11})|\" +\n" +
            "                        \"(\\\\d{7})|\" +\n" +
            "                        \"(\\\\d{8})|\" +\n" +
            "                        \"(4\\\\d{2}\\\\d{7})|\" +\n" +
            "                        \"(4\\\\d{2}-\\\\d{7})|\" +\n" +
            "                        \"(4\\\\d{2}-\\\\d{3}-\\\\d{4})|\" +\n" +
            "                        \"(4\\\\d{2}-\\\\d{4}-\\\\d{3})\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查手机输入 是否正确\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkMobilephone(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"^1(3[0-9]|4[579]|5[0-35-9]|8[0-9]|7[015-8])\\\\d{8}$\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查中文名输 入是否正确\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkChineseName(String text, int length) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"^[\\u4e00-\\u9fa5]+$\") && text.length() <= length;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查字符串中是否有空格，包括中间空格或者首尾空格\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkBlank(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"^\\\\s*|\\\\s*$\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查字符串是 否含有HTML标签\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "\n" +
            "    public static boolean checkHtmlTag(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"<(\\\\S*?)[^>]*>.*?<!--\\\\1-->|<.*? />\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查URL是 否合法\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkURL(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"[a-zA-z]+://[^\\\\s]*\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查IP是否 合法\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkIP(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"\\\\d{1,3}+\\\\.\\\\d{1,3}+\\\\.\\\\d{1,3}+\\\\.\\\\d{1,3}\");\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    /**\n" +
            "     * 检查QQ是否 合法，必须是数字，且首位不能为0，最长15位\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "\n" +
            "    public static boolean checkQQ(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"[1-9][0-9]{4,13}\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查邮编是否 合法\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkPostCode(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"[1-9]\\\\d{5}(?!\\\\d)\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查身份证是 否合法,15位或18位(或者最后一位为X)\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkIDCard(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"\\\\d{15}|\\\\d{18}|(\\\\d{17}[x|X])\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 检查输入是否 超出规定长度\n" +
            "     *\n" +
            "     * @param length\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean checkLength(String text, int length) {\n" +
            "        return ((isNullOrEmpty(text)) ? 0 : text.length()) <= length;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 判断是否为数字\n" +
            "     *\n" +
            "     * @param text\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static boolean isNumber(String text) {\n" +
            "        if (isNullOrEmpty(text)) return false;\n" +
            "        return text.matches(\"^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$\");\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 驼峰转下划线\n" +
            "     *\n" +
            "     * @param camelCaseName\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static String camel2Underline(String camelCaseName) {\n" +
            "        StringBuilder result = new StringBuilder();\n" +
            "        if (camelCaseName != null && camelCaseName.length() > 0) {\n" +
            "            result.append(camelCaseName.substring(0, 1).toLowerCase());\n" +
            "            for (int i = 1; i < camelCaseName.length(); i++) {\n" +
            "                char ch = camelCaseName.charAt(i);\n" +
            "                if (Character.isUpperCase(ch)) {\n" +
            "                    result.append(\"_\");\n" +
            "                    result.append(Character.toLowerCase(ch));\n" +
            "                } else {\n" +
            "                    result.append(ch);\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        return result.toString();\n" +
            "    }\n" +
            "\n" +
            "    public static String generateUUID() {\n" +
            "        String uuid = UUID.randomUUID().toString().\n" +
            "                replaceAll(\"-\", \"\").substring(0, 32);\n" +
            "\n" +
            "        return uuid;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * 下划线转托驼峰\n" +
            "     *\n" +
            "     * @param underscoreName\n" +
            "     * @return\n" +
            "     */\n" +
            "    public static String underline2Camel(String underscoreName) {\n" +
            "        StringBuilder result = new StringBuilder();\n" +
            "        if (underscoreName != null && underscoreName.length() > 0) {\n" +
            "            boolean flag = false;\n" +
            "            for (int i = 0; i < underscoreName.length(); i++) {\n" +
            "                char ch = underscoreName.charAt(i);\n" +
            "                if (\"_\".charAt(0) == ch) {\n" +
            "                    flag = true;\n" +
            "                } else {\n" +
            "                    if (flag) {\n" +
            "                        result.append(Character.toUpperCase(ch));\n" +
            "                        flag = false;\n" +
            "                    } else {\n" +
            "                        result.append(ch);\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "        return result.toString();\n" +
            "    }\n" +
            "\n" +
            "}\n";

    //    ################################# README 说明 ######################################
    static final String README = "1 主键字段注释中加 primarykey （必须）\n" +
            "\n" +
            "\n" +
            "2 需要模糊查询的字段注释加 like  【可选】\n" +
            "\n" +
            "\n" +
            "3 需要逻辑删除的 在字段注释加 logic [可选]\n" +
            "\n" +
            "4 application.yml中配置： \n" +
            "    # 下换线转驼峰\n" +
            "    mybatis:\n" +
            "        configuration:\n" +
            "            mapUnderscoreToCamelCase: true\n" +
            "            log-impl: org.apache.ibatis.logging.stdout.StdOutImpl";

}
