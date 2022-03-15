package spring.boot.core.map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class Mapper {

  public static ModelMapper getDefaultModelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    modelMapper.getConfiguration().setFieldMatchingEnabled(true);
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

//    Configuration configuration = modelMapper.getConfiguration();
//
//    configuration.setMatchingStrategy(MatchingStrategies.STRICT);
////    configuration.setDeepCopyEnabled(true);
////    configuration.setFullTypeMatchingRequired(true);
//    configuration.setFieldMatchingEnabled(true);

//    configuration.converterStore.getConverters().removeIf(x ->
//        x.getClass().getName().equals("org.modelmapper.internal.converter.AssignableConverter")
//            || x.getClass().getName().equals("org.modelmapper.internal.converter.CollectionConverter"));

//    configuration.converterStore.addConverter(new AssignableConverter());
//    configuration.converterStore.addConverter(new CollectionConverter());

//    configuration.setPropertyCondition(context -> {
//
//      Class<?> initialType = context.getMapping().getLastDestinationProperty().getInitialType();
//
//      if (DTO.class.isAssignableFrom(initialType)
//          && StringUtils.countMatches(context.getMapping().getPath(), '.') > 1) {
//        return false;
//      }
//
//      Class<?> destinationType = context.getDestinationType();
//      if (Collection.class.isAssignableFrom(destinationType)) {
//        Class<?> elementType = MappingContextHelper.resolveDestinationGenericType(context);
//        return !Entity.class.isAssignableFrom(elementType)
//            && !DTO.class.isAssignableFrom(elementType)
//            && !elementType.isAssignableFrom(Object.class);
//      }
//
//      return true;
//    });

    return modelMapper;
  }
}
