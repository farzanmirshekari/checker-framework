package org.checkerframework.checker.calledmethods.builder;

import com.sun.source.tree.NewClassTree;

import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.AnnotatedTypeMirror.AnnotatedExecutableType;

import javax.lang.model.element.ExecutableElement;

/**
 * Provides hooks to add CalledMethods annotations to code generated by a builder framework like
 * Lombok or AutoValue. If you are adding support to the Called Methods Checker for a new builder
 * framework, you should create a subclass of this class and modify the private method {@code
 * enableFramework} in {@link
 * org.checkerframework.checker.calledmethods.CalledMethodsAnnotatedTypeFactory}.
 *
 * <p>Every method in this class is permitted to do nothing (or always return false). The work that
 * each method must do is particular to the builder framework being supported.
 */
public interface BuilderFrameworkSupport {

    /**
     * Determines if a method is a {@code toBuilder} method on a type generated by the builder
     * framework.
     *
     * @param candidateToBuilderElement a method
     * @return {@code true} if {@code candidateToBuilderElement} is a {@code toBuilder} method on a
     *     type generated by the builder framework
     */
    boolean isToBuilderMethod(ExecutableElement candidateToBuilderElement);

    /**
     * Hook for supporting a builder framework's {@code toBuilder} routine. Typically, the returned
     * Builder has had all of its required setters invoked. So, implementations of this method
     * should add a {@link org.checkerframework.checker.calledmethods.qual.CalledMethods} annotation
     * capturing this fact.
     *
     * @param toBuilderType the type of a method that is the {@code toBuilder} method (as determined
     *     by {@link #isToBuilderMethod(ExecutableElement)}) for a type that has an associated
     *     builder
     */
    void handleToBuilderMethod(AnnotatedExecutableType toBuilderType);

    /**
     * Determines if a method is a {@code build} method on a {@code Builder} type for the builder
     * framework.
     *
     * @param candidateBuildElement a method
     * @return {@code true} if {@code candidateBuildElement} is a {@code build} method on a {@code
     *     Builder} type for the builder framework
     */
    boolean isBuilderBuildMethod(ExecutableElement candidateBuildElement);

    /**
     * Hook for adding annotations to a build() method (i.e. a finalizer) generated by a builder
     * framework.
     *
     * <p>For {@code build} methods on {@code Builder} types, implementations of this method should
     * determine the required properties and add a corresponding {@link
     * org.checkerframework.checker.calledmethods.qual.CalledMethods} annotation to the type of the
     * receiver parameter.
     *
     * @param builderBuildType the type of a method that is the {@code build} method (as determined
     *     by {@link #isBuilderBuildMethod(ExecutableElement)}) for a builder
     */
    void handleBuilderBuildMethod(AnnotatedExecutableType builderBuildType);

    /**
     * Hook for adding annotations (e.g., {@code @}{@link
     * org.checkerframework.checker.calledmethods.qual.CalledMethods}) to a constructor call.
     *
     * @param tree a constructor call
     * @param type type of the call expression
     */
    void handleConstructor(NewClassTree tree, AnnotatedTypeMirror type);
}
