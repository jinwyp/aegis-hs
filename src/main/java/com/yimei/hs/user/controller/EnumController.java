package com.yimei.hs.user.controller;

import com.yimei.hs.boot.api.Result;
import com.yimei.hs.enums.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hary on 2017/9/26.
 */
@RestController
@RequestMapping("/api")
public class EnumController {

    @GetMapping("/enums")
    public ResponseEntity<Result<List<String>>> getEnums() {
        return Result.ok(
                new ArrayList<String>(){{
                    add(BusinessType.name);
                    add(CargoArriveStatus.name);
                    add(CargoType.name);
                    add(CustomerType.name);
                    add(DiscountMode.name);
                    add(FeeClass.name);
                    add(InvoiceDirection.name);
                    add(InvoiceType.name);
                    add(OrderStatus.name);
                    add(PaymentPurpose.name);
                    add(PayMode.name);
                    add(ReceivePaymentPurpose.name);
                    add(SettleMode.name);
                    add(SettleTarget.name);
                    add(TrafficMode.name);
                    add(InStorageStatus.name);
                    add(BailType.name);
                    add(EntityType.name);
                }}
        );
    }

    @GetMapping("/enums/{type}")
    public ResponseEntity<Result<List<EnumEntity>>> getEnum(
            @PathVariable("type") String type
    ) {

        List<EnumEntity> data = null;
        if(type.equals(BusinessType.name)) {
            data = BusinessType.list();
        }
        else if (type.equals(CargoArriveStatus.name)) {
            data = CargoArriveStatus.list();
        }
        else if (type.equals(CargoType.name)) {
            data = CargoType.list();
        }
        else if (type.equals(CustomerType.name)) {
            data = CustomerType.list();
        }
        else if (type.equals(DiscountMode.name)) {
            data = DiscountMode.list();
        }
        else if (type.equals(FeeClass.name)) {
            data = FeeClass.list();
        }
        else if (type.equals(InvoiceDirection.name)) {
            data = InvoiceDirection.list();
        }
        else if (type.equals(InvoiceType.name)) {
            data = InvoiceType.list();
        }
        else if (type.equals(OrderStatus.name)) {
            data = OrderStatus.list();
        }
        else if (type.equals(PaymentPurpose.name)) {
            data = PaymentPurpose.list();
        }
        else if (type.equals(PayMode.name)) {
            data = PayMode.list();
        }
        else if (type.equals(ReceivePaymentPurpose.name)) {
            data = ReceivePaymentPurpose.list();
        }
        else if (type.equals(SettleMode.name)) {
            data = SettleMode.list();
        }
        else if (type.equals(SettleTarget.name)) {
            data = SettleTarget.list();
        }
        else if (type.equals(TrafficMode.name)) {
            data = TrafficMode.list();
        } else if (type.equals(InStorageStatus.name)) {
            data = InStorageStatus.list();
        } else if (type.equals(BailType.name)) {
            data = BailType.list();
        } else if (type.equals(EntityType.name)) {
            data = EntityType.list();
        }else {
            Result.error(4001, "此类型" + type + "不存在");
        }
        return Result.ok(data);
    }

}
