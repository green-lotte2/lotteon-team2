package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -878087951L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath buyerterms = createString("buyerterms");

    public final StringPath location = createString("location");

    public final StringPath privacy = createString("privacy");

    public final StringPath sellerterms = createString("sellerterms");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath transaction = createString("transaction");

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

