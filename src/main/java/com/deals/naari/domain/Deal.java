package com.deals.naari.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Deal.
 */
@Entity
@Table(name = "deal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Deal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Lob
    @Column(name = "deal_url")
    private String dealUrl;

    @Column(name = "posted_by")
    private String postedBy;

    @Column(name = "posted_date")
    private String postedDate;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "original_price")
    private String originalPrice;

    @Column(name = "current_price")
    private String currentPrice;

    @Column(name = "discount")
    private String discount;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "active")
    private String active;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "merchant")
    private String merchant;

    @Column(name = "tags")
    private String tags;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Deal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Deal title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Deal description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Deal imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDealUrl() {
        return this.dealUrl;
    }

    public Deal dealUrl(String dealUrl) {
        this.setDealUrl(dealUrl);
        return this;
    }

    public void setDealUrl(String dealUrl) {
        this.dealUrl = dealUrl;
    }

    public String getPostedBy() {
        return this.postedBy;
    }

    public Deal postedBy(String postedBy) {
        this.setPostedBy(postedBy);
        return this;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostedDate() {
        return this.postedDate;
    }

    public Deal postedDate(String postedDate) {
        this.setPostedDate(postedDate);
        return this;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public Deal startDate(String startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public Deal endDate(String endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOriginalPrice() {
        return this.originalPrice;
    }

    public Deal originalPrice(String originalPrice) {
        this.setOriginalPrice(originalPrice);
        return this;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrentPrice() {
        return this.currentPrice;
    }

    public Deal currentPrice(String currentPrice) {
        this.setCurrentPrice(currentPrice);
        return this;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getDiscount() {
        return this.discount;
    }

    public Deal discount(String discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return this.discountType;
    }

    public Deal discountType(String discountType) {
        this.setDiscountType(discountType);
        return this;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getActive() {
        return this.active;
    }

    public Deal active(String active) {
        this.setActive(active);
        return this;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Boolean getApproved() {
        return this.approved;
    }

    public Deal approved(Boolean approved) {
        this.setApproved(approved);
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getCountry() {
        return this.country;
    }

    public Deal country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public Deal city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public Deal pinCode(String pinCode) {
        this.setPinCode(pinCode);
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getMerchant() {
        return this.merchant;
    }

    public Deal merchant(String merchant) {
        this.setMerchant(merchant);
        return this;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getTags() {
        return this.tags;
    }

    public Deal tags(String tags) {
        this.setTags(tags);
        return this;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deal)) {
            return false;
        }
        return id != null && id.equals(((Deal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deal{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", dealUrl='" + getDealUrl() + "'" +
            ", postedBy='" + getPostedBy() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", originalPrice='" + getOriginalPrice() + "'" +
            ", currentPrice='" + getCurrentPrice() + "'" +
            ", discount='" + getDiscount() + "'" +
            ", discountType='" + getDiscountType() + "'" +
            ", active='" + getActive() + "'" +
            ", approved='" + getApproved() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", merchant='" + getMerchant() + "'" +
            ", tags='" + getTags() + "'" +
            "}";
    }
}
