package com.tsi.rooprang.DTO.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchDto {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private String status;
@SerializedName("data")
@Expose
private List<Search_Detail_DTO> data = null;


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

public List<Search_Detail_DTO> getData() {
return data;
}

public void setData(List<Search_Detail_DTO> data) {
this.data = data;
}

}