package com.yimei.hs.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 简单封装orika, 实现深度转换Bean<->Bean的Mapper.
 */
public class BeanMapper {

	private static MapperFacade mapper = null;

	static {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.getConverterFactory().registerConverter(new LocalDateConverter());
		mapperFactory.getConverterFactory().registerConverter(new LocalDateTimeConverter());
		mapper = mapperFactory.getMapperFacade();


	}

	/**
	 * 基于Dozer转换对象的类型.
	 */
	public static <S, D> D map(S source, Class<D> destinationClass) {
		return mapper.map(source, destinationClass);
	}

	/**
	 * 基于Dozer转换Collection中对象的类型.
	 */
	public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<D> destinationClass) {
		return mapper.mapAsList(sourceList, destinationClass);
	}

	public static  class LocalDateConverter extends BidirectionalConverter<LocalDate, LocalDate> {

		@Override
		public LocalDate convertTo(LocalDate source, Type<LocalDate> destinationType) {
			return (source);
		}

		@Override
		public LocalDate convertFrom(LocalDate source, Type<LocalDate> destinationType) {
			return (source);
		}

	}
	public static  class LocalDateTimeConverter extends BidirectionalConverter<LocalDateTime, LocalDateTime> {

		@Override
		public LocalDateTime convertTo(LocalDateTime source, Type<LocalDateTime> destinationType) {
			return (source);
		}

		@Override
		public LocalDateTime convertFrom(LocalDateTime source, Type<LocalDateTime> destinationType) {
			return (source);
		}

	}

}