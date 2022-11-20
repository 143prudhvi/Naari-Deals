package com.deals.naari.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.deals.naari.domain.DealType} entity. This class is used
 * in {@link com.deals.naari.web.rest.DealTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deal-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter subTitle;

    private StringFilter icon;

    private StringFilter bgColor;

    private StringFilter country;

    private StringFilter code;

    private StringFilter status;

    private BooleanFilter display;

    private Boolean distinct;

    public DealTypeCriteria() {}

    public DealTypeCriteria(DealTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.subTitle = other.subTitle == null ? null : other.subTitle.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.bgColor = other.bgColor == null ? null : other.bgColor.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.display = other.display == null ? null : other.display.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealTypeCriteria copy() {
        return new DealTypeCriteria(this);
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

    public StringFilter getIcon() {
        return icon;
    }

    public StringFilter icon() {
        if (icon == null) {
            icon = new StringFilter();
        }
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public StringFilter getBgColor() {
        return bgColor;
    }

    public StringFilter bgColor() {
        if (bgColor == null) {
            bgColor = new StringFilter();
        }
        return bgColor;
    }

    public void setBgColor(StringFilter bgColor) {
        this.bgColor = bgColor;
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

    public BooleanFilter getDisplay() {
        return display;
    }

    public BooleanFilter display() {
        if (display == null) {
            display = new BooleanFilter();
        }
        return display;
    }

    public void setDisplay(BooleanFilter display) {
        this.display = display;
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
        final DealTypeCriteria that = (DealTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(subTitle, that.subTitle) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(bgColor, that.bgColor) &&
            Objects.equals(country, that.country) &&
            Objects.equals(code, that.code) &&
            Objects.equals(status, that.status) &&
            Objects.equals(display, that.display) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subTitle, icon, bgColor, country, code, status, display, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (subTitle != null ? "subTitle=" + subTitle + ", " : "") +
            (icon != null ? "icon=" + icon + ", " : "") +
            (bgColor != null ? "bgColor=" + bgColor + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (display != null ? "display=" + display + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
