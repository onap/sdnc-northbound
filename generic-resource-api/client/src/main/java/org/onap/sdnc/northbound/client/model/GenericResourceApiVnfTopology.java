package org.onap.sdnc.northbound.client.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.onap.sdnc.northbound.client.model.GenericResourceApiVnftopologyVnfTopology;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.*;

/**
 * GenericResourceApiVnfTopology
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2025-01-08T10:24:30.980-05:00")
@JacksonXmlRootElement(localName = "GenericResourceApiVnfTopology")
@XmlRootElement(name = "GenericResourceApiVnfTopology")
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericResourceApiVnfTopology  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("vnf-topology")
  @JsonAlias({"GENERIC-RESOURCE-API:vnf-topology", "vnf-topology"})
  @JacksonXmlProperty(localName = "vnf-topology")
  private GenericResourceApiVnftopologyVnfTopology vnfTopology = null;

  public GenericResourceApiVnfTopology vnfTopology(GenericResourceApiVnftopologyVnfTopology vnfTopology) {
    this.vnfTopology = vnfTopology;
    return this;
  }

   /**
   * Get vnfTopology
   * @return vnfTopology
  **/
  @ApiModelProperty(value = "")

  @Valid

  public GenericResourceApiVnftopologyVnfTopology getVnfTopology() {
    return vnfTopology;
  }

  public void setVnfTopology(GenericResourceApiVnftopologyVnfTopology vnfTopology) {
    this.vnfTopology = vnfTopology;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenericResourceApiVnfTopology genericResourceApiVnfTopology = (GenericResourceApiVnfTopology) o;
    return Objects.equals(this.vnfTopology, genericResourceApiVnfTopology.vnfTopology);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vnfTopology);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenericResourceApiVnfTopology {\n");
    
    sb.append("    vnfTopology: ").append(toIndentedString(vnfTopology)).append("\n");
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

