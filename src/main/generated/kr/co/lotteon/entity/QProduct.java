package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 425002604L;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> cate = createNumber("cate", Integer.class);

    public final StringPath color = createString("color");

    public final StringPath company = createString("company");

    public final NumberPath<Integer> deliprice = createNumber("deliprice", Integer.class);

    public final NumberPath<Integer> delprice = createNumber("delprice", Integer.class);

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final NumberPath<Integer> hit = createNumber("hit", Integer.class);

    public final StringPath info = createString("info");

    public final StringPath opname = createString("opname");

    public final StringPath opvalue = createString("opvalue");

    public final StringPath pname = createString("pname");

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath size = createString("size");

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public final StringPath uid = createString("uid");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

