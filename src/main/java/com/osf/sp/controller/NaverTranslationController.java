package com.osf.sp.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osf.sp.dao.impl.NaverTranslationDAOImpl;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class NaverTranslationController {

	@Resource
	private NaverTranslationDAOImpl ntdao;
	
	@RequestMapping(value="/translations", method=RequestMethod.GET)
	public @ResponseBody List<Map<String,Object>> getTranslations(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		List<Map<String,Object>> tList = ntdao.selectCount();
		for(int i=0; i<tList.size(); i++) {
			tList.get(i).put("TH_RESULT", clobToString((Clob)tList.get(i).get("TH_RESULT")));
		}

//		req.setAttribute("cList", tList);
//		RequestDispatcher rd = req.getRequestDispatcher("/");
//		rd.forward(req, res);
//		log.info("{}",tList);
		return tList;
	}
	
	@RequestMapping(value="/translation/{target}/{source}/{text}", method=RequestMethod.GET)
	public @ResponseBody Object doTranslation(@PathVariable("target") String target,
												@PathVariable("source") String source,
													@PathVariable("text") String text, HttpServletRequest req) throws ServletException, IOException {
		log.info("target=>{}, source=>{}, text=>{}", new String[] {target, source, text});
		Map<String,Object> param = new HashMap<>();
		param.put("target",target);
		param.put("source",source);
		param.put("text",text);
		Map<String,Object> cMap = ntdao.selectTranslationHisOne(param);
		log.info("--------->{}",cMap);
		if(cMap==null) {
			Map<String,Object> oMap = (Map)translationTest(target, source, text);
			Map<String,Object> oMap1 = (Map)oMap.get("message");
			Map<String,Object> oMap2 = (Map)oMap1.get("result");
			String result = oMap2.get("translatedText").toString();
			param.put("result", result);
			ntdao.insertTranslationHisOne(param);
			cMap = ntdao.selectTranslationHisOne(param);
			if(cMap.get("TH_RESULT") instanceof Clob) {
				cMap.put("TH_RESULT", clobToString((Clob)cMap.get("TH_RESULT")));
			}
			return cMap;
			
		}else {
			ntdao.updateTranslationHisOne(param);
		}
			if(cMap.get("TH_RESULT") instanceof Clob) {
				cMap.put("TH_RESULT", clobToString((Clob)cMap.get("TH_RESULT")));
			}
			log.info("-=-=---==-=-=-=-=-=>{}",cMap);
		return cMap;
		
	}
	
	private Object translationTest(String target, String source, String text) {
		String clientId = "SOGegXUC9FrPOl586mxa";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "RpsW8jHNyy";//애플리케이션 클라이언트 시크릿값";
        try {
            text = URLEncoder.encode(text, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source="+source+"&target="+target+"&text="+text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            ObjectMapper om = new ObjectMapper();
            br.close();
            Map<String,Object> rMap = om.readValue(response.toString(), Map.class);
            
            return rMap;
        } catch (Exception e) {
            log.error("error=>{}",e);
            
        }
        return null;
	}
	
	private String clobToString(Clob data) {
	    StringBuilder sb = new StringBuilder();
	    try {
	        Reader reader = data.getCharacterStream();
	        BufferedReader br = new BufferedReader(reader);

	        String line;
	        while(null != (line = br.readLine())) {
	            sb.append(line);
	        }
	        br.close();
	    } catch (SQLException e) {
	    } catch (IOException e) {
	    }
	    return sb.toString();
	}
}
