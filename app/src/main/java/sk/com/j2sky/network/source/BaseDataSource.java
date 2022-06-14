package sk.com.j2sky.network.source;



import io.reactivex.rxjava3.core.Flowable;
import sk.com.j2sky.data.model.BaseResponse;

public interface BaseDataSource {

    Flowable<BaseResponse> getProfile(BaseRequest request);
}
