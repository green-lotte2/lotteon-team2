package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeller is a Querydsl query type for Seller
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeller extends EntityPathBase<Seller> {

    private static final long serialVersionUID = -1713606046L;

    public static final QSeller seller = new QSeller("seller");

    public final StringPath cohp = createString("cohp");

    public final StringPath company = createString("company");

    public final StringPath fax = createString("fax");

    public final StringPath regnum = createString("regnum");

    public final StringPath reportnum = createString("reportnum");

    public final StringPath represent = createString("represent");

    public final StringPath uid = createString("uid");

    public QSeller(String variable) {
        super(Seller.class, forVariable(variable));
    }

    public QSeller(Path<? extends Seller> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeller(PathMetadata metadata) {
        super(Seller.class, metadata);
    }

}

