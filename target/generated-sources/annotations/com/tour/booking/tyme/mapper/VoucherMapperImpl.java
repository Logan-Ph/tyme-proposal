package com.tour.booking.tyme.mapper;

import com.tour.booking.tyme.dto.request.VoucherRequest;
import com.tour.booking.tyme.dto.response.VoucherResponse;
import com.tour.booking.tyme.entity.Voucher;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-14T01:56:53+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class VoucherMapperImpl implements VoucherMapper {

    @Override
    public VoucherResponse toDTO(Voucher voucher) {
        if ( voucher == null ) {
            return null;
        }

        VoucherResponse.VoucherResponseBuilder voucherResponse = VoucherResponse.builder();

        voucherResponse.code( voucher.getCode() );
        if ( voucher.getDiscountType() != null ) {
            voucherResponse.discountType( voucher.getDiscountType().name() );
        }
        voucherResponse.discountValue( voucher.getDiscountValue() );
        voucherResponse.expirationDate( voucher.getExpirationDate() );
        voucherResponse.id( voucher.getId() );

        return voucherResponse.build();
    }

    @Override
    public Voucher toEntity(VoucherRequest request) {
        if ( request == null ) {
            return null;
        }

        Voucher.VoucherBuilder voucher = Voucher.builder();

        voucher.code( request.getCode() );
        voucher.discountType( request.getDiscountType() );
        voucher.discountValue( request.getDiscountValue() );
        voucher.expirationDate( request.getExpirationDate() );

        return voucher.build();
    }
}
