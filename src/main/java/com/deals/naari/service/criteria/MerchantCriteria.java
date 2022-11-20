package com.deals.naari.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.deals.naari.domain.Merchant} entity. This class is used
 * in {@link com.deals.naari.web.rest.MerchantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /merchants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MerchantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter title;

    private StringFilter subTitle;

    private StringFilter address;

    private StringFilter phone;

    private StringFilter country;

    private StringFilter city;

    private StringFilter imageUrl;

    private StringFilter type;

    private StringFilter location;

    private StringFilter siteUrl;

    private StringFilter status;

    private Boolean distinct;

    public MerchantCriteria() {}

    public MerchantCriteria(MerchantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.subTitle = other.subTitle == null ? null : other.subTitle.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.siteUrl = other.siteUrl == null ? null : other.siteUrl.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MerchantCriteria copy() {
        return new MerchantCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public StringFilter getSubTitle() {
        return subTitle;
    }

    public StringFilter subTitle() {
        if (subTitle == null) {
            subTitle = new StringFilter();
        }
        return subTitle;
    }

    public void setSubTitle(StringFilter subTitle) {
        this.subTitle = subTitle;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
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

    public StringFilter getLocation() {
        return location;
    }

    public StringFilter location() {
        if (location == null) {
            location = new StringFilter();
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public StringFilter getSiteUrl() {
        return siteUrl;
    }

    public StringFilter siteUrl() {
        if (siteUrl == null) {
            siteUrl = new StringFilter();
        }
        return siteUrl;
    }

    public void setSiteUrl(StringFilter siteUrl) {
        this.siteUrl = siteUrl;
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
        final MerchantCriteria that = (MerchantCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(title, that.title) &&
            Objects.equals(subTitle, that.subTitle) &&
            Objects.equals(address, that.address) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(country, that.country) &&
            Objects.equals(city, that.city) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(type, that.type) &&
            Objects.equals(location, that.location) &&
            Objects.equals(siteUrl, that.siteUrl) &&
            Objects.equals(status, that.status) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, title, subTitle, address, phone, country, city, imageUrl, type, location, siteUrl, status, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MerchantCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (subTitle != null ? "subTitle=" + subTitle + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (siteUrl != null ? "siteUrl=" + siteUrl + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
