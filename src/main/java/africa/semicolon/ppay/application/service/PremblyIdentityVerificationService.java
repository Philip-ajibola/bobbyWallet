package africa.semicolon.ppay.application.service;

import africa.semicolon.ppay.application.ports.input.premblyUseCase.VerifyIdentityUseCase;
import africa.semicolon.ppay.domain.exception.PPayWalletException;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.VerifyUserIdentityDto;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static africa.semicolon.ppay.domain.constant.PayStackAPIConstant.*;
import static africa.semicolon.ppay.infrastructure.utils.HelperClass.bufferReader;

public class PremblyIdentityVerificationService implements VerifyIdentityUseCase {
    private final Cloudinary cloudinary;
    @Value("${prembly.api.key}")
    private String PREMBLY_API_KEY;
    private ObjectMapper objectMapper = new ObjectMapper();
    public PremblyIdentityVerificationService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public StringBuilder verifyUserIdentity(VerifyUserIdentityDto userIdentity){
        String imageUrl = getImageUrl(userIdentity);
        try{
            StringEntity entity = getStringEntity(userIdentity.getNin(), imageUrl);
            StringBuilder result = new StringBuilder();
            HttpPost httpPost = createPost(entity);
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(httpPost);
            if(response.getStatusLine().getStatusCode()!= STATUS_CODE_OK) throw new PPayWalletException("User Identity Can't Be Verified");
            result = bufferReader(response, result);
            return result;
        }catch (Exception e){
            throw new PPayWalletException("prembly verification failed Reason: "+ e.getMessage() );
        }
        
    }

    private HttpPost createPost(StringEntity entity) {
        HttpPost httpPost = new HttpPost(PREMBLY_VERIFY_WITH_NIN);
        httpPost.setHeader("content-type", MediaType.APPLICATION_JSON_VALUE);
        httpPost.setHeader("accept", "application/json");
        httpPost.setHeader("x-api-key", PREMBLY_API_KEY);
        httpPost.setHeader("app_id","BobbyWallet");
        httpPost.setEntity(entity);
        return httpPost;
    }

    private StringEntity getStringEntity(String nin, String imageUrl) throws JsonProcessingException, UnsupportedEncodingException {
        Map<String, String> request  = new HashMap<>();
        request.put("image", imageUrl);
        request.put("number", nin);
        String json=objectMapper.writeValueAsString(request);
        StringEntity entity = new StringEntity(json);
        return entity;
    }

    private String getImageUrl(VerifyUserIdentityDto userIdentity) {
        String imageUrl ="";
        try {
            Map map = ObjectUtils.asMap("resource_type", "auto");
            Map<?, ?> response = cloudinary.uploader().upload(userIdentity.getImageFile().getBytes(), map);
            imageUrl= response.get("url").toString();

        } catch (IOException e) {
            throw new PPayWalletException("media upload failed Reason: "+ e.getMessage() );
        }
        return imageUrl;
    }

}
