package iris.bjx.test1.JsonBeans;

import java.util.List;

public class BTJsonBean {
    private String from;
    private String to;
    private List<TransResuBean> trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<TransResuBean> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<TransResuBean> trans_result) {
        this.trans_result = trans_result;
    }

}
