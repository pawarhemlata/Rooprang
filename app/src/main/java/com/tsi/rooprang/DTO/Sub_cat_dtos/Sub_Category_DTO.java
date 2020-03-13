package com.tsi.rooprang.DTO.Sub_cat_dtos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sub_Category_DTO {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private String status;
@SerializedName("data")
@Expose
private List<Sub_Cat_Details_DTO> data = null;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public List<Sub_Cat_Details_DTO> getData() {
return data;
}

public void setData(List<Sub_Cat_Details_DTO> data) {
this.data = data;
}

}