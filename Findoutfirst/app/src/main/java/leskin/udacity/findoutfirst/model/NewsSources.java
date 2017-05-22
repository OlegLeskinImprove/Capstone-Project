
package leskin.udacity.findoutfirst.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewsSources extends BaseModel implements Serializable{

    @SerializedName("sources")
    @Expose
    private List<Source> sources = null;

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

}
