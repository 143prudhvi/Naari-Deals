package com.deals.naari.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.deals.naari.domain.Notification} entity. This class is used
 * in {@link com.deals.naari.web.rest.NotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userId;

    private StringFilter title;

    private StringFilter message;

    private StringFilter status;

    private StringFilter type;

    private StringFilter dateOfRead;

    private StringFilter originalPrice;

    private StringFilter currentPrice;

    private StringFilter discount;

    private StringFilter discountType;

    private Boolean distinct;

    public NotificationCriteria() {}

    public NotificationCriteria(NotificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.dateOfRead = other.dateOfRead == null ? null : other.dateOfRead.copy();
        this.originalPrice = other.originalPrice == null ? null : other.originalPrice.copy();
        this.currentPrice = other.currentPrice == null ? null : other.currentPrice.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.discountType = other.discountType == null ? null : other.discountType.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NotificationCriteria copy() {
        return new NotificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public StringFilter userId() {
        if (userId == null) {
            userId = new StringFilter();
        }
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getMessage() {
        return message;
    }

    public StringFilter message() {
        if (message == null) {
            message = new StringFilter();
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getDateOfRead() {
        return dateOfRead;
    }

    public StringFilter dateOfRead() {
        if (dateOfRead == null) {
            dateOfRead = new StringFilter();
        }
        return dateOfRead;
    }

    public void setDateOfRead(StringFilter dateOfRead) {
        this.dateOfRead = dateOfRead;
    }

    public StringFilter getOriginalPrice() {
        return originalPrice;
    }

    public StringFilter originalPrice() {
        if (originalPrice == null) {
            originalPrice = new StringFilter();
        }
        return originalPrice;
    }

    public void setOriginalPrice(StringFilter originalPrice) {
        this.originalPrice = originalPrice;
    }

    public StringFilter getCurrentPrice() {
        return currentPrice;
    }

    public StringFilter currentPrice() {
        if (currentPrice == null) {
            currentPrice = new StringFilter();
        }
        return currentPrice;
    }

    public void setCurrentPrice(StringFilter currentPrice) {
        this.currentPrice = currentPrice;
    }

    public StringFilter getDiscount() {
        return discount;
    }

    public StringFilter discount() {
        if (discount == null) {
            discount = new StringFilter();
        }
        return discount;
    }

    public void setDiscount(StringFilter discount) {
        this.discount = discount;
    }

    public StringFilter getDiscountType() {
        return discountType;
    }

    public StringFilter discountType() {
        if (discountType == null) {
            discountType = new StringFilter();
        }
        return discountType;
    }

    public void setDiscountType(StringFilter discountType) {
        this.discountType = discountType;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotificationCriteria that = (NotificationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(title, that.title) &&
            Objects.equals(message, that.message) &&
            Objects.equals(status, that.status) &&
            Objects.equals(type, that.type) &&
            Objects.equals(dateOfRead, that.dateOfRead) &&
            Objects.equals(originalPrice, that.originalPrice) &&
            Objects.equals(currentPrice, that.currentPrice) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(discountType, that.discountType) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            userId,
            title,
            message,
            status,
            type,
            dateOfRead,
            originalPrice,
            currentPrice,
            discount,
            discountType,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (message != null ? "message=" + message + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (dateOfRead != null ? "dateOfRead=" + dateOfRead + ", " : "") +
            (originalPrice != null ? "originalPrice=" + originalPrice + ", " : "") +
            (currentPrice != null ? "currentPrice=" + currentPrice + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (discountType != null ? "discountType=" + discountType + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
