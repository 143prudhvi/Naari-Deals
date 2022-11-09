package com.deals.naari.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.deals.naari.domain.Slide} entity. This class is used
 * in {@link com.deals.naari.web.rest.SlideResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /slides?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SlideCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter subTitle;

    private StringFilter status;

    private StringFilter country;

    private StringFilter startDate;

    private StringFilter endDate;

    private StringFilter imageUrl;

    private StringFilter dealUrl;

    private StringFilter tags;

    private Boolean distinct;

    public SlideCriteria() {}

    public SlideCriteria(SlideCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.subTitle = other.subTitle == null ? null : other.subTitle.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.dealUrl = other.dealUrl == null ? null : other.dealUrl.copy();
        this.tags = other.tags == null ? null : other.tags.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SlideCriteria copy() {
        return new SlideCriteria(this);
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

    public StringFilter getStartDate() {
        return startDate;
    }

    public StringFilter startDate() {
        if (startDate == null) {
            startDate = new StringFilter();
        }
        return startDate;
    }

    public void setStartDate(StringFilter startDate) {
        this.startDate = startDate;
    }

    public StringFilter getEndDate() {
        return endDate;
    }

    public StringFilter endDate() {
        if (endDate == null) {
            endDate = new StringFilter();
        }
        return endDate;
    }

    public void setEndDate(StringFilter endDate) {
        this.endDate = endDate;
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

    public StringFilter getDealUrl() {
        return dealUrl;
    }

    public StringFilter dealUrl() {
        if (dealUrl == null) {
            dealUrl = new StringFilter();
        }
        return dealUrl;
    }

    public void setDealUrl(StringFilter dealUrl) {
        this.dealUrl = dealUrl;
    }

    public StringFilter getTags() {
        return tags;
    }

    public StringFilter tags() {
        if (tags == null) {
            tags = new StringFilter();
        }
        return tags;
    }

    public void setTags(StringFilter tags) {
        this.tags = tags;
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
        final SlideCriteria that = (SlideCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(subTitle, that.subTitle) &&
            Objects.equals(status, that.status) &&
            Objects.equals(country, that.country) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(dealUrl, that.dealUrl) &&
            Objects.equals(tags, that.tags) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subTitle, status, country, startDate, endDate, imageUrl, dealUrl, tags, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SlideCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (subTitle != null ? "subTitle=" + subTitle + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (dealUrl != null ? "dealUrl=" + dealUrl + ", " : "") +
            (tags != null ? "tags=" + tags + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
