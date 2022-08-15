package io.github.opencubicchunks.cc_core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks that a class must be replaced by the class in {@code value}
 *
 * If the target is a class (or abstract class), use a class (or abstract class)
 * If the target is an interface, use an interface
 *
 * {@link RetentionPolicy#RUNTIME} to make things easier to debug, it's not actually necessary
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DeclaresClass {
    /**
     * Class to replace it with in the form {@code the.package.name.ClassName}
     */
    String value();
}
