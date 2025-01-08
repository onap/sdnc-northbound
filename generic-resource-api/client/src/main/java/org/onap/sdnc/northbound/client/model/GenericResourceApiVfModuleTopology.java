package org.onap.sdnc.northbound.client.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.onap.sdnc.northbound.client.model.GenericResourceApiVfmoduletopologyVfModuleTopology;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.*;

/**
 * GenericResourceApiVfModuleTopology
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2025-01-08T14:16:16.769-05:00")
@JacksonXmlRootElement(localName = "GenericResourceApiVfModuleTopology")
@XmlRootElement(name = "GenericResourceApiVfModuleTopology")
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericResourceApiVfModuleTopology  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("vf-module-topology")
  @JsonAlias({"GENERIC-RESOURCE-API:vf-module-topology", "vf-module-topology"})
  @JacksonXmlProperty(localName = "vf-module-topology")
  private GenericResourceApiVfmoduletopologyVfModuleTopology vfModuleTopology = null;

  public GenericResourceApiVfModuleTopology vfModuleTopology(GenericResourceApiVfmoduletopologyVfModuleTopology vfModuleTopology) {
    this.vfModuleTopology = vfModuleTopology;
    return this;
  }

   /**
   * Get vfModuleTopology
   * @return vfModuleTopology
  **/
  @ApiModelProperty(value = "")

  @Valid

  public GenericResourceApiVfmoduletopologyVfModuleTopology getVfModuleTopology() {
    return vfModuleTopology;
  }

  public void setVfModuleTopology(GenericResourceApiVfmoduletopologyVfModuleTopology vfModuleTopology) {
    this.vfModuleTopology = vfModuleTopology;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenericResourceApiVfModuleTopology genericResourceApiVfModuleTopology = (GenericResourceApiVfModuleTopology) o;
    return Objects.equals(this.vfModuleTopology, genericResourceApiVfModuleTopology.vfModuleTopology);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vfModuleTopology);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenericResourceApiVfModuleTopology {\n");
    
    sb.append("    vfModuleTopology: ").append(toIndentedString(vfModuleTopology)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

