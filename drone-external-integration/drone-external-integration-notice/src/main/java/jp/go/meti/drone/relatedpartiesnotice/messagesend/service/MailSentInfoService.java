package jp.go.meti.drone.relatedpartiesnotice.messagesend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.go.meti.drone.com.common.util.MessageUtils;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.MailSentInfoEntity;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper.MailSentInfoMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * メール周知履歴mail_sent_infoに保存するサービス
 */
@Slf4j
@Service
public class MailSentInfoService {

	@Autowired
	private MailSentInfoMapper mailSentInfoMapper;

	/**
	 * テーブルmail_sent_infoに保存
	 * 
	 * @param mailSentInfo メール送信結果
	 * @throws Exception 例外
	 */
	@Transactional
	public void saveMailSentInfo(MailSentInfoEntity mailSentInfo) throws Exception {
		try {
			mailSentInfoMapper.insertMailSentInfo(mailSentInfo);
		} catch (DataAccessException e) {
			// ＤＢアクセスエラーが発生しました。メッセージとして出力される
			String message = MessageUtils.getMessage("DR000E001");
			log.error(message,e);
			throw e;
		} catch (Exception e) {
			// 挿入失敗時
			String message = MessageUtils.getMessage("DRC01E001");
			log.error(message,e);
			throw e;
		}
	}



}
