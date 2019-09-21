1 需要模糊查询的字段注释加 like  【可选】


2 需要逻辑删除的 在字段注释加 logic [可选]

3 application.yml中配置： 
    # 下换线转驼峰
    mybatis:
        configuration:
            mapUnderscoreToCamelCase: true
            log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

4 如果存在多个主键，请把本表的主键放在第一个顺序