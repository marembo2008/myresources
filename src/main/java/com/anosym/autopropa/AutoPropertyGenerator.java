/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.autopropa;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;


/**
 *
 * @author marembo
 */
@Mojo(name = "auto", defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class AutoPropertyGenerator extends AbstractMojo {

  @Parameter(property = "project.runtimeClasspathElements", required = true, readonly = true)
  private List<String> classpath;
  /**
   * Generated source path
   */
  @Parameter(required = true, defaultValue = "target/generated-sources/auto-properties")
  private String generatedSourcesPath;
  @Parameter(required = true, defaultValue = "src/main/resources/properties")
  private String propertyFilePaths;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
  }

  private void init() {
    try {
      Set<URL> urls = new HashSet<>();
      for (String element : classpath) {
        urls.add(new File(element).toURI().toURL());
      }
      ClassLoader contextClassLoader = URLClassLoader.newInstance(
              urls.toArray(new URL[0]),
              Thread.currentThread().getContextClassLoader());
      Thread.currentThread().setContextClassLoader(contextClassLoader);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

}
