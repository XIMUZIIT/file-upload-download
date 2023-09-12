package org.wxf.file.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

/**
 * 通用工具类
 *
 * @author wxf
 * @date 2023-08-18
 */

public class GeneralTools {


	/**
	 * 实体对象集合的转化工具类
	 */
	public static <T> List<T> dtoListTransfer(List<?> sourceEntityList, Class<T> targetClass) {
		// 判断dto是否为空!
		if (CollectionUtils.isEmpty(sourceEntityList)) {
			return null;
		}
		// 判断DoClass 是否为空
		if (targetClass == null) {
			return null;
		}
		try {
			List<T> objects = new ArrayList<>();
			for (Object object : sourceEntityList) {
				T newInstance = targetClass.newInstance();
				BeanUtils.copyProperties(object, newInstance);
				objects.add(newInstance);
			}
			return objects;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 实体对象转换工具
	 */
	public static <T> T dtoTransfer(Object sourceEntity, Class<T> targetClass) {
		// 判断dto是否为空!
		if (sourceEntity == null) {
			return null;
		}
		// 判断DoClass 是否为空
		if (targetClass == null) {
			return null;
		}
		try {
			T newInstance = targetClass.newInstance();
			BeanUtils.copyProperties(sourceEntity, newInstance);
			// Dto转换Do
			return newInstance;
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * list<T> 转 list<map<string,object>工具类
	 *
	 * @param objectList 入参集合
	 * @param <T>        泛型
	 * @return 出参对象
	 */
	public static <T> List<Map<String, Object>> objListToMapList(List<T> objectList) {
		List<Map<String, Object>> res = new ArrayList<>();
		if (!CollectionUtils.isEmpty(objectList)) {
			for (T o : objectList) {
				res.add(JSONObject.parseObject(JSON.toJSONString(o), Map.class));
			}
		}
		return res;
	}

	/**
	 * list<map<string,object> 转 list<T> 工具类
	 *
	 * @param mapList List<Map<String, Object>>
	 * @param clazz   要转的实体对象类
	 * @param <T>
	 * @return 实体集合对象
	 */
	public static <T> List<T> mapListToObjectList(List<Map<String, Object>> mapList,
			Class<T> clazz) {
		List<T> res = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			res.add(JSON.parseObject(JSON.toJSONString(map), clazz));
		}
		return res;
	}

}
