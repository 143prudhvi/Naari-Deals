package com.deals.naari.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.deals.naari.domain.MemberType} entity. This class is used
 * in {@link com.deals.naari.web.rest.MemberTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /member-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter memberType;

    private StringFilter description;

    private Boolean distinct;

    public MemberTypeCriteria() {}

    public MemberTypeCriteria(MemberTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.memberType = other.memberType == null ? null : other.memberType.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MemberTypeCriteria copy() {
        return new MemberTypeCriteria(this);
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

    public StringFilter getMemberType() {
        return memberType;
    }

    public StringFilter memberType() {
        if (memberType == null) {
            memberType = new StringFilter();
        }
        return memberType;
    }

    public void setMemberType(StringFilter memberType) {
        this.memberType = memberType;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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
        final MemberTypeCriteria that = (MemberTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(memberType, that.memberType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberType, description, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (memberType != null ? "memberType=" + memberType + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
