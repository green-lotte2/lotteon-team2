package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTerms is a Querydsl query type for Terms
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTerms extends EntityPathBase<Terms> {

    private static final long serialVersionUID = -2132558268L;

    public static final QTerms terms = new QTerms("terms");

    public final StringPath buyerterms = createString("buyerterms");

    public final StringPath location = createString("location");

    public final StringPath privacy = createString("privacy");

    public final StringPath sellerterms = createString("sellerterms");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath transaction = createString("transaction");

    public QTerms(String variable) {
        super(Terms.class, forVariable(variable));
    }

    public QTerms(Path<? extends Terms> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTerms(PathMetadata metadata) {
        super(Terms.class, metadata);
    }

}

