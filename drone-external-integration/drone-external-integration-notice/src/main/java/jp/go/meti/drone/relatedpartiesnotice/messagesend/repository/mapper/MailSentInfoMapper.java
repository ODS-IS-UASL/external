package jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import jp.go.meti.drone.relatedpartiesnotice.messagesend.repository.entity.MailSentInfoEntity;

/**
 * メール周知履歴MAIL_SENT_INFO Mapper
 */
@Mapper
public interface MailSentInfoMapper {

    /**
     * メール周知履歴インサート
     * 
     * @param mailSentInfo
     */
    void insertMailSentInfo(MailSentInfoEntity mailSentInfo);



    /**
     * 事業者IDよりメール周知履歴
     * 
     * @param mailSeq
     * @param operatorId
     * @return 返答
     */
    MailSentInfoEntity getMailSentInfoById(int mailSeq, String operatorId);
 
    
    
    @Delete("DELETE FROM mail_sent_info WHERE operator_id = #{operatorId}")
	void deleteMailSentInfoById(String operatorId);

}
