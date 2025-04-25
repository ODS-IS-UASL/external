package jp.go.meti.drone.relatedpartiesnotice.airwaytenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.apache.ibatis.javassist.NotFoundException;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseBadRequestError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseInternalServerError;
import jp.go.meti.drone.dips.model.commonmodel.CommonResponseNotFoundError;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.com.BadRequestException;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.RequestRouteTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.RequestUpdateRouteTenantInfo;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.model.ResponseAirwayTenant;
import jp.go.meti.drone.relatedpartiesnotice.airwaytenant.service.AirwayTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 航路事業者情報コントローラー
 * 
 * @version 1.0 2024/11/08
 */
@RestController
@RequestMapping("${drone-route-base-path}")
@RequiredArgsConstructor
@Slf4j
public class AirwayTenantControllerImpl implements AirwayTenantController {

    @Autowired
    @Qualifier("AirwayTenantService")
    private AirwayTenantService airwayTenantService;

    /**
     * 航路事業者情報を登録する
     * <p>
     * 指定した航路IDと事業者情報を紐づけ登録するAPIです。
     * </p>
     *
     * @param Request Body (required)
     * @return 登録成功 (status code 200) or Bad Request (status code 400) or Not Found (status code 404) or Internal Server Error
     *         サーバーエラー (status code 500)
     */
    @PostMapping("/airwayTenantLink")
    public ResponseEntity<?> addAirwayTenant(@RequestBody @Validated RequestRouteTenantInfo request) {
        log.info("航路事業者情報を登録コントローラー開始します。");
        try {
            airwayTenantService.addTenantAssociation(
                request.getAirwayId(),
                request.getRelatedPartiesIdList());
            log.info("航路事業者情報を登録コントローラー終了します。");
            return ResponseAirwayTenant.successResponse();
        } catch (BadRequestException e) {
            // 400 Bad Request
            CommonResponseBadRequestError badRequestError = new CommonResponseBadRequestError(
                HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return badRequestError.errorResponse();
        } catch (NotFoundException e) {
            // 404 Not Found
            CommonResponseNotFoundError notFoundError = new CommonResponseNotFoundError(
                HttpStatus.NOT_FOUND.value(), e.getMessage());
            return notFoundError.errorResponse();
        } catch (Exception e) {
            // 500 Internal Server Error サーバーエラー
            CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return internalServerError.errorResponse();
        }
    }

    /**
     * 航路事業者情報を更新する
     * </p>
     * 指定した航路IDに紐づく事業者情報を更新するAPIです。
     * </p>
     *
     * @param Request Body (required)
     * @return 更新成功 (status code 200) or Bad Request (status code 400) or Not Found (status code 404) or Internal Server Error
     *         サーバーエラー (status code 500)
     */
    @PutMapping("/airwayTenantLink")
    public ResponseEntity<?> updateAirwayTenant(
        @RequestBody @Validated RequestUpdateRouteTenantInfo request) {
        log.info("航路事業者情報を更新コントローラー開始します。");
        if(request.getAirwayId()!=null && (request.getRelatedPartiesIdList().isEmpty() || request.getRelatedPartiesIdList() == null )) {
            try {
                airwayTenantService.deleteAirwayTenant(request.getAirwayId());
                log.info("航路事業者情報を更新コントローラー終了します。");
                return ResponseAirwayTenant.successResponse();
            } catch (BadRequestException e) {
                // 400 Bad Request
                CommonResponseBadRequestError badRequestError = new CommonResponseBadRequestError(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
                return badRequestError.errorResponse();
            } catch (NotFoundException e) {
                // 404 Not Found
                CommonResponseNotFoundError notFoundError = new CommonResponseNotFoundError(
                    HttpStatus.NOT_FOUND.value(), e.getMessage());
                return notFoundError.errorResponse();
            } catch (Exception e) {
                // 500 Internal Server Error サーバーエラー
                CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
                return internalServerError.errorResponse();
            }
        } else {
        	try {
        		airwayTenantService.updateAirwayTenant(
        			request.getUpdateType(),
        			request.getAirwayId(),
        			request.getRelatedPartiesIdList());
        		log.info("航路事業者情報を更新コントローラー終了します。");
        		return ResponseAirwayTenant.successResponse();
        	} catch (BadRequestException e) {
        		// 400 Bad Request
        		CommonResponseBadRequestError badRequestError = new CommonResponseBadRequestError(
        			HttpStatus.BAD_REQUEST.value(), e.getMessage());
        		return badRequestError.errorResponse();
        	} catch (NotFoundException e) {
        		// 404 Not Found
        		CommonResponseNotFoundError notFoundError = new CommonResponseNotFoundError(
        			HttpStatus.NOT_FOUND.value(), e.getMessage());
        		return notFoundError.errorResponse();
        	} catch (Exception e) {
        		// 500 Internal Server Error サーバーエラー
        		CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
        			HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        		return internalServerError.errorResponse();
        	}
        }
    }

    /**
     * 航路事業者情報を削除する
     * </p>
     * 指定した航路IDに紐づく事業者情報を削除するAPIです。
     * </p>
     *
     * @param airwayId 削除対象の航路ID (required)
     * @return 削除成功 (status code 200) or Bad Request (status code 400) or Not Found (status code 404) or Internal Server Error
     *         サーバーエラー (status code 500)
     */
    @DeleteMapping("/airwayTenantLink")
    public ResponseEntity<?> deleteAirwayTenant(String airwayId) {
        log.info("航路事業者情報を削除コントローラー開始します。");
        try {
            airwayTenantService.deleteAirwayTenant(airwayId);
            log.info("航路事業者情報を削除コントローラー終了します。");
            return ResponseAirwayTenant.successResponse();
        } catch (BadRequestException e) {
            // 400 Bad Request
            CommonResponseBadRequestError badRequestError = new CommonResponseBadRequestError(
                HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return badRequestError.errorResponse();
        } catch (NotFoundException e) {
            // 404 Not Found
            CommonResponseNotFoundError notFoundError = new CommonResponseNotFoundError(
                HttpStatus.NOT_FOUND.value(), e.getMessage());
            return notFoundError.errorResponse();
        } catch (Exception e) {
            // 500 Internal Server Error サーバーエラー
            CommonResponseInternalServerError internalServerError = new CommonResponseInternalServerError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return internalServerError.errorResponse();
        }
    }
}
