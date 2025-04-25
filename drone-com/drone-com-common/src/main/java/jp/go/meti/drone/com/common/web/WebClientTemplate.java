package jp.go.meti.drone.com.common.web;

import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

/**
 * 接続先ごとに初期設定したWebClientを業務から利用するためのtemplateクラス。
 * 
 * @version $Revision$
 */
@RequiredArgsConstructor
public class WebClientTemplate {

    /**
     * 何もしないConsumer定義。
     */
    private static final Consumer<HttpHeaders> DO_NOTHING = headers -> {
    };

    /**
     * baseUrlなどの初期設定をしたWebClient。
     */
    private final WebClient webClient;

    /**
     * Form送信時に使用するContentType。
     */
    private final MediaType contentTypeForm;

    /**
     * WebClientでタイムアウト発生したときのデフォルトのリトライ設定。
     */
    private final Retry retrySpec;

    /**
     * 送信処理前にWebClientへの共通処理を差し込むためのFunction定義。
     * 
     * <pre>
     * ここで設定したFunctionは、webClientを使用した処理実行ごとに事前にwebLlientの設定を修正するために使用
     * 実行時にデフォルト設定をしたWebClientを渡すので追加設定したWebClientを生成して返却する
     * デフォルトでは何も追加設定せず使用する
     * </pre>
     */
    private UnaryOperator<WebClient> beforeSendUnaryOperator = UnaryOperator.identity();

    /**
     * コンストラクタ。
     * 
     * @param webClient baseUrlなどの初期設定をしたWebClient
     * @param contentTypeForm Form送信時に使用するContentType
     * @param retrySpec WebClientでタイムアウト発生したときのデフォルトのリトライ設定
     * @param beforeSendUnaryOperator 送信処理前に保持するWebClientへの共通処理を都度差し込むためのFunction定義
     */
    public WebClientTemplate(WebClient webClient, MediaType contentTypeForm, Retry retrySpec,
        UnaryOperator<WebClient> beforeSendUnaryOperator) {
        this(webClient, contentTypeForm, retrySpec);
        this.beforeSendUnaryOperator = beforeSendUnaryOperator;
    }

    /**
     * 指定されたURIへGET送信し、取得したレスポンス情報を返却する。
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param path 送信先のpath
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> get(String path, Class<R> responseBodyClass) {
        return doGet(builder -> builder.path(path).build(), DO_NOTHING, responseBodyClass).block();
    }

    /**
     * 指定されたURIへGET送信し、取得したレスポンス情報を返却する。
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param path 送信時のpath情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @param headersConsumer header情報を加工するためのConsumer
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> get(String path, Consumer<HttpHeaders> headersConsumer, Class<R> responseBodyClass) {
        return doGet(builder -> builder.path(path).build(), headersConsumer, responseBodyClass).block();
    }

    /**
     * 指定されたURIへGET送信し、取得したレスポンス情報を返却する。
     * 
     * <pre>
     * GET送信時にタイムアウトが発生した場合は、リトライ設定に従いリトライする。
     * </pre>
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param path 送信時のpath情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> getWithRetry(String path, Class<R> responseBodyClass) {
        return doGet(builder -> builder.path(path).build(), DO_NOTHING, responseBodyClass).retryWhen(retrySpec).block();
    }

    /**
     * 指定されたURIへGET送信し、取得したレスポンス情報を返却する。
     * 
     * <pre>
     * GET送信時にタイムアウトが発生した場合は、リトライ設定に従いリトライする。
     * </pre>
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param path 送信時のpath情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @param headersConsumer header情報を加工するためのConsumer
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> getWithRetry(String path, Consumer<HttpHeaders> headersConsumer,
        Class<R> responseBodyClass) {
        return doGet(builder -> builder.path(path).build(), headersConsumer, responseBodyClass).retryWhen(retrySpec)
            .block();
    }

    /**
     * 指定されたpathにクエリパラメータを付与してGET送信し、取得したレスポンス情報を返却する。
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param path 送信時のpath情報
     * @param params 送信時のクエリパラメータ情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> get(String path, MultiValueMap<String, String> params, Class<R> responseBodyClass) {
        return doGet(builder -> builder.path(path).queryParams(params).build(), DO_NOTHING, responseBodyClass).block();
    }

    /**
     * 指定されたpathにクエリパラメータを付与してGET送信し、取得したレスポンス情報を返却する。
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param path 送信時のpath情報
     * @param params 送信時のクエリパラメータ情報
     * @param headersConsumer header情報を加工するためのConsumer
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> get(String path, MultiValueMap<String, String> params,
        Consumer<HttpHeaders> headersConsumer, Class<R> responseBodyClass) {
        return doGet(builder -> builder.path(path).queryParams(params).build(), headersConsumer, responseBodyClass)
            .block();
    }

    /**
     * 指定されたpathにクエリパラメータを付与してGET送信し、取得したレスポンス情報を返却する。
     * 
     * <pre>
     * GET送信時にタイムアウトが発生した場合は、リトライ設定に従いリトライする。
     * </pre>
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param path 送信時のpath情報
     * @param params 送信時のクエリパラメータ情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> getWithRetry(String path, MultiValueMap<String, String> params,
        Class<R> responseBodyClass) {
        return doGet(builder -> builder.path(path).queryParams(params).build(), DO_NOTHING, responseBodyClass)
            .retryWhen(retrySpec)
            .block();
    }

    /**
     * 指定されたpathにクエリパラメータを付与してGET送信し、取得したレスポンス情報を返却する。
     * 
     * <pre>
     * GET送信時にタイムアウトが発生した場合は、リトライ設定に従いリトライする。
     * </pre>
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param path 送信時のpath情報
     * @param params 送信時のクエリパラメータ情報
     * @param headersConsumer header情報を加工するためのConsumer
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> getWithRetry(String path, MultiValueMap<String, String> params,
        Consumer<HttpHeaders> headersConsumer, Class<R> responseBodyClass) {
        return doGet(builder -> builder.path(path).queryParams(params).build(), headersConsumer, responseBodyClass)
            .retryWhen(retrySpec)
            .block();
    }

    /**
     * 指定されたURIに指定されたリクエスト情報をPOST送信し、取得したレスポンス情報を返却する。
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param uri 送信時のURI情報
     * @param requestBody 送信時のリクエスト情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> post(String uri, Object requestBody, Class<R> responseBodyClass) {
        return doSend(HttpMethod.POST, uri, DO_NOTHING, requestBody, responseBodyClass).block();
    }

    /**
     * 指定されたURIに指定されたリクエスト情報をPOST送信し、取得したレスポンス情報を返却する。
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param uri 送信時のURI情報
     * @param headersConsumer header情報を加工するためのConsumer
     * @param requestBody 送信時のリクエスト情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> post(String uri, Consumer<HttpHeaders> headersConsumer, Object requestBody,
        Class<R> responseBodyClass) {
        return doSend(HttpMethod.POST, uri, headersConsumer, requestBody, responseBodyClass).block();
    }

    /**
     * 指定されたURIに指定されたリクエスト情報をPOST送信し、取得したレスポンス情報を返却する。
     * 
     * <pre>
     * POST送信時にタイムアウトが発生した場合は、リトライ設定に従いリトライする。
     * </pre>
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param uri 送信時のURI情報
     * @param requestBody 送信時のリクエスト情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> postWithRetry(String uri, Object requestBody, Class<R> responseBodyClass) {
        return doSend(HttpMethod.POST, uri, DO_NOTHING, requestBody, responseBodyClass).retryWhen(retrySpec).block();
    }

    /**
     * 指定されたURIに指定されたリクエスト情報をPOST送信し、取得したレスポンス情報を返却する。
     * 
     * <pre>
     * POST送信時にタイムアウトが発生した場合は、リトライ設定に従いリトライする。
     * </pre>
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param uri 送信時のURI情報
     * @param headersConsumer header情報を加工するためのConsumer
     * @param requestBody 送信時のリクエスト情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> postWithRetry(String uri, Consumer<HttpHeaders> headersConsumer, Object requestBody,
        Class<R> responseBodyClass) {
        return doSend(HttpMethod.POST, uri, headersConsumer, requestBody, responseBodyClass).retryWhen(retrySpec)
            .block();
    }

    /**
     * 指定されたURIに指定されたForm情報をPOST送信し、取得したレスポンス情報を返却する。
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param uri 送信時のURI情報
     * @param requestFormData 送信時のForm情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> postForm(String uri, MultiValueMap<String, String> requestFormData,
        Class<R> responseBodyClass) {
        return doSend(
            HttpMethod.POST,
            uri,
            headers -> headers.setContentType(contentTypeForm),
            requestFormData,
            responseBodyClass).block();
    }

    /**
     * 指定されたURIに指定されたForm情報をPOST送信し、取得したレスポンス情報を返却する。
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param uri 送信時のURI情報
     * @param headersConsumer header情報を加工するためのConsumer
     * @param requestFormData 送信時のForm情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> postForm(String uri, Consumer<HttpHeaders> headersConsumer,
        MultiValueMap<String, String> requestFormData, Class<R> responseBodyClass) {
        Consumer<HttpHeaders> baseHeader = headers -> headers.setContentType(contentTypeForm);
        return doSend(HttpMethod.POST, uri, baseHeader.andThen(headersConsumer), requestFormData, responseBodyClass)
            .block();
    }

    /**
     * 指定されたURIに指定されたForm情報をPOST送信し、取得したレスポンス情報を返却する。
     * 
     * <pre>
     * POST送信時にタイムアウトが発生した場合は、リトライ設定に従いリトライする。
     * </pre>
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param uri 送信時のURI情報
     * @param requestFormData 送信時のForm情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> postFormWithRetry(String uri, MultiValueMap<String, String> requestFormData,
        Class<R> responseBodyClass) {
        return doSend(
            HttpMethod.POST,
            uri,
            headers -> headers.setContentType(contentTypeForm),
            requestFormData,
            responseBodyClass).retryWhen(retrySpec).block();
    }

    /**
     * 指定されたURIに指定されたForm情報をPOST送信し、取得したレスポンス情報を返却する。
     * 
     * <pre>
     * POST送信時にタイムアウトが発生した場合は、リトライ設定に従いリトライする。
     * </pre>
     * 
     * @param <R> レスポンスボディをマッピングする型定義
     * @param uri 送信時のURI情報
     * @param headersConsumer header情報を加工するためのConsumer
     * @param requestFormData 送信時のForm情報
     * @param responseBodyClass レスポンスボディをマッピングするクラス定義
     * @return 取得したレスポンス情報
     */
    public <R> ResponseEntity<R> postFormWithRetry(String uri, Consumer<HttpHeaders> headersConsumer,
        MultiValueMap<String, String> requestFormData, Class<R> responseBodyClass) {
        Consumer<HttpHeaders> baseHeader = headers -> headers.setContentType(contentTypeForm);
        return doSend(HttpMethod.POST, uri, baseHeader.andThen(headersConsumer), requestFormData, responseBodyClass)
            .retryWhen(retrySpec)
            .block();
    }

    /**
     * 初期設定したWebClientを使用して、HTTP通信を処理するコールバックインタフェースを提供する。
     * 
     * @param <T> コールバック関数の入力情報の型の定義
     * @param <R> コールバック関数で返却する型の定義
     * @param inputData コールバック関数で使用する入力情報
     * @param doSendFunction WebClientを利用して結果を生成するためのコールバック関数
     * @return コールバック関数で生成した返却情報
     */
    public <T, R> R send(T inputData, BiFunction<WebClient, T, R> doSendFunction) {
        return doSendFunction.apply(beforeSendUnaryOperator.apply(webClient), inputData);
    }

    <R> Mono<ResponseEntity<R>> doGet(Function<UriBuilder, URI> uriFuncion, Consumer<HttpHeaders> headersConsumer,
        Class<R> responseBodyClass) {
        return beforeSendUnaryOperator.apply(webClient)
            .get()
            .uri(uriFuncion)
            .headers(headersConsumer)
            .retrieve()
            .toEntity(responseBodyClass);
    }

    <R> Mono<ResponseEntity<R>> doSend(HttpMethod method, String uri, Consumer<HttpHeaders> headersConsumer,
        Object body, Class<R> responseBodyClass) {
        return beforeSendUnaryOperator.apply(webClient)
            .method(method)
            .uri(uri)
            .headers(headersConsumer)
            .bodyValue(body)
            .retrieve()
            .toEntity(responseBodyClass);
    }

}
