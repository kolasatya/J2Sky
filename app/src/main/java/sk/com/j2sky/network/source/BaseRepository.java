package sk.com.j2sky.network.source;



import io.reactivex.rxjava3.core.Flowable;
import sk.com.j2sky.data.model.BaseResponse;

public class BaseRepository implements BaseDataSource{

    private BaseDataSource baseDataSource;

    private BaseRepository(BaseDataSource baseDataSource){
        this.baseDataSource = baseDataSource;
    }

    public static BaseRepository getInstance(BaseDataSource baseDataSource){
        return new BaseRepository(baseDataSource);
    }

    @Override
    public Flowable<BaseResponse> getProfile(BaseRequest request) {
        return baseDataSource.getProfile(request);
    }
}
