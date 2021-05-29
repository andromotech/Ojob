package andromo.ojob.RestCall;
import andromo.ojob.model.NsView;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Server {
    @GET("/job/nj.json")
    Call<NsView> getEightBook();
}

