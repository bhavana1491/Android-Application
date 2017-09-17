package com.example.mycongressapp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Bhavana on 11/21/16.
 */

public class CommitteeModel {

    private int count;
    private PageBean page;
    private List<ResultsBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class PageBean {


        private int count;
        private int per_page;
        private int page;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    public static class ResultsBean implements Serializable,Comparable<CommitteeModel.ResultsBean>{

        private String chamber;
        private String committee_id;
        private String name;
        private String office;
        private String phone;
        private boolean subcommittee;
        private String parent_committee_id;

        public String getChamber() {
            return chamber;
        }

        public void setChamber(String chamber) {
            this.chamber = chamber;
        }

        public String getCommittee_id() {
            return committee_id;
        }

        public void setCommittee_id(String committee_id) {
            this.committee_id = committee_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOffice() {
            return office;
        }

        public void setOffice(String office) {
            this.office = office;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public boolean isSubcommittee() {
            return subcommittee;
        }

        public void setSubcommittee(boolean subcommittee) {
            this.subcommittee = subcommittee;
        }

        public String getParent_committee_id() {
            return parent_committee_id;
        }

        public void setParent_committee_id(String parent_committee_id) {
            this.parent_committee_id = parent_committee_id;
        }

        @Override
        public int compareTo(ResultsBean resultsBean) {
            return 0;
        }
    }
}
