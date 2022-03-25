package com.jianmu.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用 阿里巴巴 fastjson 进行json格式处理
 *
 * @author lime
 */
@Configuration
public class CommonsFastJsonConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        //将Long  转为String    防止精度丢失 四舍五入
        // ToStringSerializer 是这个包 com.alibaba.fastjson.serializer.ToStringSerializer
        SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        config.setSerializeConfig(serializeConfig);

        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,// 保留map空的字段
                SerializerFeature.WriteNullListAsEmpty,//将List类型的null转成[]
                SerializerFeature.WriteNullStringAsEmpty,//将String类型的null转成""
                SerializerFeature.WriteNullNumberAsZero,//将Number类型的null转成0
                SerializerFeature.WriteNullBooleanAsFalse,//将Boolean类型的null转成false
//                SerializerFeature.PrettyFormat,  //json 格式化
                SerializerFeature.WriteDateUseDateFormat, //日期格式转换
                SerializerFeature.DisableCircularReferenceDetect    // 避免循环引用
        );


        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        fastJsonHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        // 添加支持的MediaTypes;不添加时默认为*/*,也就是默认支持全部
        // 但是MappingJackson2HttpMessageConverter里面支持的MediaTypes为application/json
        // 参考它的做法, fast json也只添加application/json的MediaType
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        //设置fast json  index 0  防止json不生效
        converters.add(0, fastJsonHttpMessageConverter);
    }

}
