package org.wxf.file.tools;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wangxiaofei
 * @version 1.0
 * @description: MyBatisPlus代码逆向生成工具
 * @date 2022/05/05 17:21
 */
public class MybatisPlusTools {

	public static void main(String[] args) {
		/**
		 * 代码生成器
		 */
		AutoGenerator mpg = new AutoGenerator();

		/**
		 * 全局配置
		 */
		GlobalConfig globalConfig = new GlobalConfig();
		//生成文件的输出目录   D:\mlj\docker-project\src\main\java\com\example\demo
		String projectPath = "D:\\MyProject\\file-upoad-downoad\\file";
		//"D:\\devlop\\MljProject\\basic.distribution";
		globalConfig.setOutputDir(projectPath+"/java");
		//Author设置作者
		globalConfig.setAuthor("wangxiaofei");
		//是否覆盖文件
		globalConfig.setFileOverride(true);
		globalConfig.setOpen(false);
		globalConfig.setIdType(IdType.AUTO);
		globalConfig.setBaseColumnList(true);
		globalConfig.setBaseResultMap(true);
		globalConfig.setFileOverride(true);
		globalConfig.setServiceName("%sService");
		globalConfig.setXmlName("%sMapper");
		globalConfig.setSwagger2(true); //实体属性 Swagger2 注解
		mpg.setGlobalConfig(globalConfig);

		/**
		 * 数据源配置
		 */

    /*DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:oracle:thin:@//20.26.90.24:1521/PDB_PUB");
    // dsc.setSchemaName("public");
    dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
    dsc.setUsername("cmpv3_test");
    dsc.setPassword("cmpv3_testQAZ");
    mpg.setDataSource(dsc);*/
		DataSourceConfig dsc = new DataSourceConfig();
		// 数据库类型,默认MYSQL
		dsc.setDbType(DbType.MYSQL);
		//自定义数据类型转换
		dsc.setTypeConvert(new MySqlTypeConvert());
		dsc.setUrl(
				"jdbc:mysql://47.97.40.236:3306/haoyaer?useUnicode=true&characterEncoding=utf-8&useSSL=false");
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("123456789.");
		mpg.setDataSource(dsc);

		/**
		 * 包配置
		 */
		PackageConfig pc = new PackageConfig();
		pc.setParent(
				"org.wxf.file");  //D:\devlop\MljProject\base.org\src\main\java\ltd\mlj\cmss\service\base\org
		pc.setXml("mapper");
		mpg.setPackageInfo(pc);

		/**
		 * 自定义配置
		 */
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		/**
		 * 模板
		 */
		//如果模板引擎是 freemarker
		String templatePath = "/templates/mapper.xml.ftl";
		// 如果模板引擎是 velocity
		// String templatePath = "/templates/mapper.xml.vm";

		/**
		 * 自定义输出配置
		 */
		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
				return projectPath + "/resources/mapper/" + tableInfo.getEntityName()
						+ "Mapper" + StringPool.DOT_XML;
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);

		/**
		 * 配置模板
		 */
		TemplateConfig templateConfig = new TemplateConfig();

		// 配置自定义输出模板
		//指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
		// templateConfig.setEntity("templates/entity2.java");
		// templateConfig.setService();
		// templateConfig.setController();

		templateConfig.setXml(null);
		mpg.setTemplate(templateConfig);

		/**
		 * 策略配置
		 */
		StrategyConfig strategy = new StrategyConfig();
		//设置命名格式
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setControllerMappingHyphenStyle(true);
		strategy.setEntityLombokModel(true);//是否生成lombook实体
		strategy.setRestControllerStyle(true);
		//驼峰转连字符
		strategy.setControllerMappingHyphenStyle(true);
		//表名前缀
		//strategy.setTablePrefix("w_");
		//strategy.setSuperEntityColumns("ID", "CREATED_TIME", "UPDATED_TIME", "CREATED_BY", "UPDATED_BY", "STATUS");
		//此处要生成的表名  一一列出
		strategy.setInclude(
				"file_upload"
		);
		// strategy.setSuperEntityClass("com.zhikun.entity");

		mpg.setStrategy(strategy);
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

}
