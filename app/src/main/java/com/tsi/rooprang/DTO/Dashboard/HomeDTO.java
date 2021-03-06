
package com.tsi.rooprang.DTO.Dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeDTO {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("data")
        @Expose
        private Home_Detail_DTO data;

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

        public Home_Detail_DTO getData() {
            return data;
        }

        public void setData(Home_Detail_DTO data) {
            this.data = data;
        }

}
