---
title: Custom Spring Editors
layout: default
--- # Categories
- java
- programming
- spring
- tidbits
---

I had the need recently to inject an array of strings (<tt>java.lang.String[]</tt>) into a bean property and I was curious about whether or not I could inject the strings as comma-separated values (CSV). With a little poking around in the <a href="http://springframework.org">Spring API</a> I found that the supporting <tt>PropertyEditor</tt> is already there, but not configured by default. My next question was about how you go about configuring custom property editors.

Configuring custom property editors is quite easy, you add a <tt>CustomEditorConfigurer</tt> bean to your context which will register itself with the bean factory at load-time. By mapping your custom editors to the <tt>CustomEditorConfigurer</tt>, you register them with the enclosing bean factory... pretty simple:

<code lang="xml">
<bean id="customEditorConfigurer" 
         class="org.springframework.beans.factory.config.CustomEditorConfigurer">
    <property name="customEditors">
        <map>
            <entry key="java.lang.String[]">
                <bean class="org.springframework.beans.propertyeditors.StringArrayPropertyEditor">
                    <constructor-arg value=":" />
                </bean>
            </entry>
        </map>
    </property>
</bean>
</code>

Note that the value of the entry key attribute is the full class name of the property type to be handled by the editor. Only one editor can be registered for a given type. The <tt>StringArrayPropertyEditor</tt> is available with the core Spring API and it will convert a delimited string into a string array. The delimiter is configurable as a constructor argument; it defaults to comma, but I have overridden it here to use a colon in this case as an example. Once you have this in place, the added configuration work is done.

Let's create a simple test bean to ensure that the editor is registered properly:

<code lang="java">
public class SomeBean {
    private String[] array;

    public void setArray(String[] array) {this.array = array;}
    public String[] getArray() {return array;}
}</code>

Add it to the spring context:

<code lang="xml">
<bean id="someBean" class="SomeBean">
    <property name="array" value="one:two:three:four" />
</bean>
</code>

Now if you load the context and pull the bean out you will find that the array property contains four elements, with values of "one", "two", "three", and "four" respectively. It's just that easy!

Just to verify that we have not lost any pre-existing functionality, you can add another bean that loads the array using the spring list tag:

<code lang="java">
<bean id="someBean2" class="SomeBean">
    <property name="array">
       <list>
          <value>alpha</value>
          <value>bravo</value>
          <value>charlie</value>
       </list>
    </property>
</bean>
</code>

You will notice that this method still works fine as well.

There are a few other custom editors that spring provides in the <tt>org.springframework.bean.propertyeditors</tt> package, and it is also quite easy to implement your own, but I will save that for another day.

<b>More Information</b>

<a href="http://static.springframework.org/spring/docs/2.0.x/reference/validation.html#beans-beans-conversion">SpringFramework: Chapter 5: Validation, Data-binding, the BeanWrapper, and PropertyEditors</a>

