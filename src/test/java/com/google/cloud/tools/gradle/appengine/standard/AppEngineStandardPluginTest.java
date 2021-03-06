/*
 * Copyright (c) 2016 Google Inc. All Right Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.google.cloud.tools.gradle.appengine.standard;

import com.google.cloud.tools.gradle.appengine.BuildResultFilter;
import com.google.cloud.tools.gradle.appengine.core.AppEngineCorePlugin;
import com.google.cloud.tools.gradle.appengine.core.DeployExtension;
import com.google.cloud.tools.gradle.appengine.util.ExtensionUtil;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.gradle.api.Project;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/** Test App Engine Standard Plugin configuration. */
public class AppEngineStandardPluginTest {

  @Rule public final TemporaryFolder testProjectDir = new TemporaryFolder();

  private void setUpTestProject() throws IOException {
    Path buildFile = testProjectDir.getRoot().toPath().resolve("build.gradle");
    InputStream buildFileContent =
        getClass()
            .getClassLoader()
            .getResourceAsStream("projects/AppEnginePluginTest/build.gradle");
    Files.copy(buildFileContent, buildFile);

    Path webInf = testProjectDir.getRoot().toPath().resolve("src/main/webapp/WEB-INF");
    Files.createDirectories(webInf);
    File appengineWebXml = Files.createFile(webInf.resolve("appengine-web.xml")).toFile();
    Files.write(appengineWebXml.toPath(), "<appengine-web-app/>".getBytes(Charsets.UTF_8));
  }

  @Test
  public void testDeploy_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineDeploy", "--dry-run")
            .build();

    final List<String> expected =
        ImmutableList.of(
            ":compileJava",
            ":processResources",
            ":classes",
            ":war",
            ":explodeWar",
            ":assemble",
            ":appengineStage",
            ":appengineDeploy");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testDeployCron_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineDeployCron", "--dry-run")
            .build();

    final List<String> expected =
        ImmutableList.of(
            ":compileJava",
            ":processResources",
            ":classes",
            ":war",
            ":explodeWar",
            ":assemble",
            ":appengineStage",
            ":appengineDeployCron");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testDeployDispatch_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineDeployDispatch", "--dry-run")
            .build();

    final List<String> expected =
        ImmutableList.of(
            ":compileJava",
            ":processResources",
            ":classes",
            ":war",
            ":explodeWar",
            ":assemble",
            ":appengineStage",
            ":appengineDeployDispatch");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testDeployDos_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineDeployDos", "--dry-run")
            .build();

    final List<String> expected =
        ImmutableList.of(
            ":compileJava",
            ":processResources",
            ":classes",
            ":war",
            ":explodeWar",
            ":assemble",
            ":appengineStage",
            ":appengineDeployDos");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testDeployIndex_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineDeployIndex", "--dry-run")
            .build();

    final List<String> expected =
        ImmutableList.of(
            ":compileJava",
            ":processResources",
            ":classes",
            ":war",
            ":explodeWar",
            ":assemble",
            ":appengineStage",
            ":appengineDeployIndex");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testDeployQueue_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineDeployQueue", "--dry-run")
            .build();

    final List<String> expected =
        ImmutableList.of(
            ":compileJava",
            ":processResources",
            ":classes",
            ":war",
            ":explodeWar",
            ":assemble",
            ":appengineStage",
            ":appengineDeployQueue");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testRun_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineRun", "--dry-run")
            .build();

    final List<String> expected =
        ImmutableList.of(
            ":compileJava",
            ":processResources",
            ":classes",
            ":war",
            ":explodeWar",
            ":assemble",
            ":appengineRun");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testStart_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineStart", "--dry-run")
            .build();

    final List<String> expected =
        ImmutableList.of(
            ":compileJava",
            ":processResources",
            ":classes",
            ":war",
            ":explodeWar",
            ":assemble",
            ":appengineStart");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testStop_taskTree() throws IOException {
    setUpTestProject();
    BuildResult buildResult =
        GradleRunner.create()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath()
            .withArguments("appengineStop", "--dry-run")
            .build();

    final List<String> expected = Collections.singletonList(":appengineStop");

    Assert.assertEquals(expected, BuildResultFilter.extractTasks(buildResult));
  }

  @Test
  public void testDefaultConfiguration() throws IOException {
    File appengineWebXml =
        new File(testProjectDir.getRoot(), "src/main/webapp/WEB-INF/appengine-web.xml");
    appengineWebXml.getParentFile().mkdirs();
    appengineWebXml.createNewFile();
    Files.write(appengineWebXml.toPath(), "<appengine-web-app/>".getBytes());

    Project p = ProjectBuilder.builder().withProjectDir(testProjectDir.getRoot()).build();
    p.getPluginManager().apply(JavaPlugin.class);
    p.getPluginManager().apply(WarPlugin.class);
    p.getPluginManager().apply(AppEngineStandardPlugin.class);
    ((ProjectInternal) p).evaluate();

    ExtensionAware ext =
        (ExtensionAware) p.getExtensions().getByName(AppEngineCorePlugin.APPENGINE_EXTENSION);
    DeployExtension deployExt = new ExtensionUtil(ext).get(AppEngineCorePlugin.DEPLOY_EXTENSION);
    StageStandardExtension stageExt =
        new ExtensionUtil(ext).get(AppEngineStandardPlugin.STAGE_EXTENSION);
    RunExtension run = new ExtensionUtil(ext).get(AppEngineStandardPlugin.RUN_EXTENSION);

    Assert.assertEquals(
        new File(p.getBuildDir(), "exploded-" + p.getName()), stageExt.getSourceDirectory());
    Assert.assertEquals(new File(p.getBuildDir(), "staged-app"), stageExt.getStagingDirectory());
    Assert.assertEquals(
        new File(p.getBuildDir(), "staged-app/WEB-INF/appengine-generated"),
        deployExt.getAppEngineDirectory());
    Assert.assertEquals(
        Collections.singletonList(new File(p.getBuildDir(), "staged-app/app.yaml")),
        deployExt.getDeployables());
    Assert.assertEquals(
        Collections.singletonList(new File(p.getBuildDir(), "exploded-" + p.getName())),
        run.getServices());
    Assert.assertFalse(new File(testProjectDir.getRoot(), "src/main/docker").exists());
    Assert.assertEquals(20, run.getStartSuccessTimeout());
  }
}
