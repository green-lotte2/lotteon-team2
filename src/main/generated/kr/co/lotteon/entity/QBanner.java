package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBanner is a Querydsl query type for Banner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBanner extends EntityPathBase<Banner> {

    private static final long serialVersionUID = 2091033103L;

    public static final QBanner banner = new QBanner("banner");

    public final StringPath bcolor = createString("bcolor");

    public final DatePath<java.time.LocalDate> bendDate = createDate("bendDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> bendTime = createTime("bendTime", java.time.LocalTime.class);

    public final StringPath bfile = createString("bfile");

    public final StringPath blink = createString("blink");

    public final StringPath blocation = createString("blocation");

    public final NumberPath<Integer> bmanage = createNumber("bmanage", Integer.class);

    public final StringPath bname = createString("bname");

    public final NumberPath<Integer> bno = createNumber("bno", Integer.class);

    public final DatePath<java.time.LocalDate> bstartDate = createDate("bstartDate", java.time.LocalDate.class);

    public final TimePath<java.time.LocalTime> bstartTime = createTime("bstartTime", java.time.LocalTime.class);

    public QBanner(String variable) {
        super(Banner.class, forVariable(variable));
    }

    public QBanner(Path<? extends Banner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBanner(PathMetadata metadata) {
        super(Banner.class, metadata);
    }

}

