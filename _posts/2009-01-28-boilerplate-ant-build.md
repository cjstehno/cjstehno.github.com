---
title: Boilerplate Ant Build
layout: default
--- # Categories
- ant
- development
- java
- programming
---

I have found it very useful to create a reusable boiler plate <a href="http://ant.apache.org">Ant</a> <tt>build.xml</tt> file that I use as a starting point for all my projects. With a little bit of configuration property tweaking and perhaps a task modification here or there you have a standardized build environment which can be used by your IDE or on the command line.

I have provided a copy of my boilerplate build file at the bottom of this posting. It is provided for use under a creative commons license (<a rel="license" target="_blank" href="http://creativecommons.org/licenses/by/3.0/us/">Creative Commons Attribution 3.0 United States License</a>), so please maintain the copyright header if you use or extend it.

If you look at the file you will see that the first section has all the properties you need. I used in-file properties rather than a separate properties file because I don't really change them often for any given project and it keeps everything all in one neat little file. Technically you could get more re-usability by using properties files, but that assumes that you wont be modifying the build file itself for your project, which is something I do from time to time if something is not quite right for the project.

The first thing you will want to do is update the project name attribute to reflect the name of your project. Then you will want to update the configuration properties so that everything is correct for your build environment. I tried to keep everything pretty standard so that they don't need to change much between projects.

The first property is the <tt>war.name</tt> which is the name you want the generated war file to have. I usually leave it set to the default, which is the project name.

<code lang="xml"><property name="war.name" value="${ant.project.name}" /></code>
	
The next set of properties are the locations of your source directories, which default to the same values that I have set in my IDE.

<code lang="xml">
<property name="src.dir" value="src" />
<property name="test.src.dir" value="test" />
<property name="web.src.dir" value="web" />
</code>

and then your external library directory.

<code lang="xml"><property name="lib.dir" value="lib" /></code>

There has been some confusion around the external library directory property when I have shown this to others. This is not where all your libraries go, but where you put the libraries you <em>don't</em> want in the war file. Things like JUnit and JMock jars go in there so that they are usable for testing and compiling but you don't really want them to go into your deployed artifact. I the jars in this directory mapped on my classpath in my IDE too while those in the WEB-INF/lib are picked up by default.

The next group of properties define the artifact build locations:

<code lang="xml">
<property name="build.dir" value="build" />
<property name="src.build.dir" value="${build.dir}/classes" />
<property name="test.build.dir" value="${build.dir}/test-classes" />
<property name="test.report.dir" value="${build.dir}/test-reports" />
<property name="webapp.build.dir" value="${build.dir}/webapp" />
</code>

As you can see, even if your <tt>build</tt> directory is called something else, like <tt>bin</tt> you can change everything else by updating that one property, <tt>build.dir</tt>. 

The next property is the location of your local web server, where the war file would be deployed locally. The default is my symlink to the <tt>webapps</tt> directory for my local <a href="http://tomcat.apache.org">Tomcat</a> server installation.

<code lang="xml"><property name="deploy.local.dir" value="/usr/local/tomcat/webapps" /></code>

The final two properties are more environmental. You want to specifiy the JVM version you are targetting and whether or not you want debugging information to be compiled with your classes.

<code lang="xml">
<property name="jvm.version" value="1.6" />
<property name="debug.enabled" value="true" />
</code>

You probably wont change these all that often, though it might be a good idea to disable debugging on production builds; I will have to look into supporting that. The <tt>jvm.version</tt> setting is nice because I think Ant still defaults to 1.3 or something like that.
	
The next section of the file contains all the task definitions. You will want to tweak these every now and then if you have special needs. You can run the Ant project help command (<tt>ant -p</tt>) to see the descriptions for all the tasks. Yes, I actually added descriptions for all of them.

Some common tasks I use a lot are:

<pre>ant clean-all test</pre>
	
Run all the tests on a clean build.
	
<pre>ant clean-all redeploy-local</pre>

Clean the build content and do a full local server redeployment.
	
<pre>ant clean-all test war</pre>
	
Run all the tests on a clean build and produce the war file.

I have used this in about five different projects now and it comes in really handy to have a standardized base point, especially when you are in a hurry and trying to do a quick command line build of the project; your commands are the same across your projects.

The follow-up article to this one will delve more into using the Ant build as a tool set and about adding scripting layers on top of the build to make repeated tasks bulletproof and quick.

Let me know if you have any suggestions for modifications or additions to this basic build script. It is a work in progress as I try to bring more simplicity into my development processes.

<code lang="xml">
<?xml version='1.0' ?>

<!--
    Ant Build Template (v1.0)
    Copyright (c) 2009 Christopher J. Stehno (chris@stehno.com)
    Creative Commons Attribution 3.0 United States License (http://creativecommons.org/licenses/by/3.0/us/)
    You are permitted to use this file in any personal and/or commercial product as long as you adhere to the above license.
-->

<project name="project-name" default="war">
	
	<!-- ============== -->
	<!-- Configurations -->
	<!-- ============== -->
	
    <property name="war.name" value="${ant.project.name}" />

    <property name="src.dir" value="src" />
    <property name="test.src.dir" value="test" />

    <property name="web.src.dir" value="web" />
    <property name="lib.dir" value="lib" />

    <property name="build.dir" value="build" />
    <property name="src.build.dir" value="${build.dir}/classes" />
    <property name="test.build.dir" value="${build.dir}/test-classes" />
    <property name="test.report.dir" value="${build.dir}/test-reports" />
    <property name="webapp.build.dir" value="${build.dir}/webapp" />

    <property name="deploy.local.dir" value="/usr/local/tomcat/webapps" />

    <property name="jvm.version" value="1.6" />
    <property name="debug.enabled" value="true" />

    <path id="classpath">
        <fileset dir="${web.src.dir}/WEB-INF/lib" includes="**/*.jar" />
        <fileset dir="${lib.dir}" includes="**/*.jar" />
    </path>

    <path id="test.classpath">

        <path refid="classpath" />
        <pathelement path="${src.build.dir}" />
    </path>

    <path id="test.runtime.classpath">
        <path refid="test.classpath" />
        <pathelement path="${test.build.dir}" />
    </path>

    <!-- ======= -->
    <!-- Targets -->
	<!-- ======= -->
	
    <target name="compile" description="Compiles the java sources.">
        <echo>Compiling java sources...</echo>
        <mkdir dir="${src.build.dir}" />
        <javac destdir="${src.build.dir}" classpathref="classpath" source="${jvm.version}" target="${jvm.version}" debug="${debug.enabled}">
            <src path="${src.dir}" />
            <include name="**/*.java" />
        </javac>

        <echo>Compiling java test sources...</echo>
        <mkdir dir="${test.build.dir}" />
        <javac destdir="${test.build.dir}" classpathref="test.classpath" source="${jvm.version}" target="${jvm.version}" debug="${debug.enabled}">
            <src path="${test.src.dir}" />
            <include name="**/*.java" />
        </javac>

        <echo>Copying over confg files...</echo>
        <copy todir="${src.build.dir}">
            <fileset dir="${src.dir}">
                <include name="**/*.xml" />
                <include name="**/*.properties" />
            </fileset>
        </copy>
    </target>

    <target name="clean-compile" description="Cleans up the compiled java sources.">
        <delete dir="${src.build.dir}" />
        <delete dir="${test.build.dir}" />
    </target>

    <target name="test" depends="compile" description="Runs all the tests (compiles if necessary).">
        <echo>Running unit tests...</echo>
        <mkdir dir="${test.report.dir}" />

        <junit printsummary="yes" haltonfailure="yes">
            <classpath refid="test.runtime.classpath" />
            <formatter type="plain" />
            <batchtest fork="yes" todir="${test.report.dir}">
                <fileset dir="${test.build.dir}" includes="**/*Test.class" />
            </batchtest>
        </junit>
    </target>

    <target name="clean-test" description="Cleans out all of the tests and test data.">
        <delete dir="${test.report.dir}" />
    </target>

    <target name="webapp" depends="compile" description="Builds the exploded web application (compiles if necessary).">
        <echo>Building web application...</echo>

        <mkdir dir="${webapp.build.dir}" />
        <copy todir="${webapp.build.dir}">
            <fileset dir="${web.src.dir}">
                <include name="**/*.*" />
            </fileset>
        </copy>

        <mkdir dir="${webapp.build.dir}/WEB-INF/classes" />
        <copy todir="${webapp.build.dir}/WEB-INF/classes">
            <fileset dir="${src.build.dir}">
                <include name="**/*.*" />
            </fileset>
        </copy>
    </target>

    <target name="clean-webapp" description="Cleans up the exploded web application.">
        <delete dir="${webapp.build.dir}" />
    </target>

    <target name="war" depends="webapp" description="Builds the war file (builds the webapp if necessary).">
        <echo>Building the war file...</echo>
        <jar destfile="${build.dir}/${war.name}.war">
            <fileset dir="${webapp.build.dir}" />
        </jar>
    </target>

    <target name="clean-war" description="Cleans up the war file.">
        <delete file="${build.dir}/${war.name}.war" />
    </target>

    <target name="clean-all" depends="clean-compile,clean-test,clean-webapp,clean-war" description="Cleans up all generated content." />

    <target name="deploy-local" depends="war" description="Deploys the war to the locally configured server location.">
        <echo>Deploying war to local server (${deploy.local.dir})...</echo>
        <copy todir="${deploy.local.dir}" file="${build.dir}/${war.name}.war">
        </copy>
    </target>

    <target name="undeploy-local" description="Removes the war (and exploded webapp) from the local server.">
        <delete file="${deploy.local.dir}/${war.name}.war" />
        <delete dir="${deploy.local.dir}/${war.name}" />
    </target>

    <target name="deploy-remote" depends="war,-prompt-for-server" description="Deploys the war file to the configured remote server (scp only).">
        <echo>Deploying war file to remote server via scp...</echo>
        <scp file="${build.dir}/${war.name}.war" todir="${deploy.remote.conn}" />
        <echo>You will need to finish the deployment from the server box.</echo>
    </target>

    <target name="-prompt-for-server" unless="deploy.remote.conn">
        <input message="Enter connection string (user:pass@server:path):" addproperty="deploy.remote.conn" defaultvalue="" />
    </target>

    <target name="archive" depends="clean-all" description="Cleans up and archives the project in a time-stampped zip file one directory up.">
        <echo>Creating archive of project...</echo>
        <tstamp />
        <zip destfile="../${ant.project.name}-${DSTAMP}-${TSTAMP}.zip" compress="yes" basedir="." />
    </target>

    <target name="sync-webapp" description="Syncs up the locally configured webapp content with the project.">
        <echo>Sync-ing the web code with local exploded war...</echo>
        <copy todir="${deploy.local.dir}/${war.name}">
            <fileset dir="${web.src.dir}">
                <exclude name="WEB-INF/**/*.*" />
            </fileset>
        </copy>
    </target>

    <target name="redeploy-local" depends="undeploy-local,deploy-local" description="Redeploys the local webapp (undeploy and deploy)." />
</project>
</code>

<blockquote>You can find an updated version of this build script in my <a href="http://github.com/cjstehno/AntBoilerplate">AntBoilerplate</a> project.</blockquote>
