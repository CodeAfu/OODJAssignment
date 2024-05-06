package com.ags.pms.models;

// MADE BY STUDENT - Information derived from PresentationSlot
public class PresentationRequest extends Request {

    private String module;
    private boolean isApproved;

    public PresentationRequest() {
        super();
    }

    public PresentationRequest(int id, User user, String module) {
        super(id, user);
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    
}
