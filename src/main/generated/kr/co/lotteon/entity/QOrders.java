package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = -1816361528L;

    public static final QOrders orders = new QOrders("orders");

    public final StringPath addr1 = createString("addr1");

    public final StringPath addr2 = createString("addr2");

    public final StringPath hp = createString("hp");

    public final DatePath<java.time.LocalDate> odate = createDate("odate", java.time.LocalDate.class);

    public final NumberPath<Integer> ono = createNumber("ono", Integer.class);

    public final StringPath payment = createString("payment");

    public final StringPath receiver = createString("receiver");

    public final NumberPath<Integer> savepoint = createNumber("savepoint", Integer.class);

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public final StringPath uid = createString("uid");

    public final NumberPath<Integer> usepoint = createNumber("usepoint", Integer.class);

    public final StringPath zip = createString("zip");

    public QOrders(String variable) {
        super(Orders.class, forVariable(variable));
    }

    public QOrders(Path<? extends Orders> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrders(PathMetadata metadata) {
        super(Orders.class, metadata);
    }

}

