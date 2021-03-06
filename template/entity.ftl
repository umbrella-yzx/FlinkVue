package ${entity.javaPackage};

<#list entity.inPackages as package>
    <#if (package)??>import ${package};</#if>
</#list>
import java.util.*;

/**
* This code is generated by FreeMarker
*
*/
public class ${entity.className}<#if entity.superclass?has_content> extends ${entity.superclass} </#if>
{
/********** attribute ***********/
<#list entity.properties as property>
    public ${property.javaType} ${property.propertyName} ${property.suffix};

</#list>
/********** constructors ***********/
<#if entity.constructors>
    public ${entity.className}() {

    }

    public ${entity.className}(<#list entity.properties as property>${property.javaType} ${property.propertyName}<#if property_has_next>, </#if></#list>) {
    <#list entity.properties as property>
        this.${property.propertyName} = ${property.propertyName};
    </#list>
    }
</#if>

    @Override
    public String toString() {
        return "${entity.className}{" +
            <#list entity.properties as property>
                "${property.propertyName}="+${property.propertyName}<#if property_has_next>+"," </#if>+
            </#list>
            '}';
    }

    public String toCSVString(){
        return <#list entity.properties as property>${property.propertyName}<#if property_has_next>+","+</#if></#list>;
    }

}
