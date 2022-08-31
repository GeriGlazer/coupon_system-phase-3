package com.jb.coupon3.repositories;

import com.jb.coupon3.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    Coupon findByTitleAndCompanyId(String title, int companyID);
    Boolean existsByTitleAndCompanyId(String title, int companyID);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM coupons WHERE (end_Date) < curdate()", nativeQuery = true)
    void deleteCouponsByDate();


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT * FROM customer_vs_coupons WHERE coupon_id=?1 AND customer_id=?2", nativeQuery = true)
    List<Coupon> isCouponPurchased(int coupon_id, int customer_id);

}
