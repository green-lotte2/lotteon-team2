package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductimg is a Querydsl query type for Productimg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductimg extends EntityPathBase<Productimg> {

    private static final long serialVersionUID = -310908457L;

    public static final QProductimg productimg = new QProductimg("productimg");

    public final StringPath detailimg = createString("detailimg");

    public final StringPath mainimg = createString("mainimg");

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final StringPath subimg = createString("subimg");

    public QProductimg(String variable) {
        super(Productimg.class, forVariable(variable));
    }

    public QProductimg(Path<? extends Productimg> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductimg(PathMetadata metadata) {
        super(Productimg.class, metadata);
    }

}

