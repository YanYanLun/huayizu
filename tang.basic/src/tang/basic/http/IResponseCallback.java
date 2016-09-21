package tang.basic.http;

public interface IResponseCallback<T> {
	public void onResponse(T data,String requestclass);
	public void onError(TxException error,String requestclass);
}
