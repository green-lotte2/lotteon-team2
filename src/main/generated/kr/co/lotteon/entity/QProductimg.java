package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductimg is a Querydsl query type for Productimg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductimg extends EntityPathBase<Productimg> {

    private static final long serialVersionUID = -310908457L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductimg productimg = new QProductimg("productimg");

    public final StringPath detailimg = createString("detailimg");

    public final StringPath mainimg = createString("mainimg");

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final QProduct product;

    public final StringPath subimg = createString("subimg");

    public QProductimg(String variable) {
        this(Productimg.class, forVariable(variable), INITS);
    }

    public QProductimg(Path<? extends Productimg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductimg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductimg(PathMetadata metadata, PathInits inits) {
        this(Productimg.class, metadata, inits);
    }

    public QProductimg(Class<? extends Productimg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

