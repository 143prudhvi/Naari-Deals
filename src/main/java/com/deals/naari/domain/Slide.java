package com.deals.naari.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Slide.
 */
@Entity
@Table(name = "slide")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Slide implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "status")
    private String status;

    @Column(name = "country")
    private String country;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "merchant_icon")
    private String merchantIcon;

    @Lob
    @Column(name = "deal_url")
    private String dealUrl;

    @Column(name = "tags")
    private String tags;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Slide id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Slide title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public Slide subTitle(String subTitle) {
        this.setSubTitle(subTitle);
        return this;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getStatus() {
        return this.status;
    }

    public Slide status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return this.country;
    }

    public Slide country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public Slide startDate(String startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public Slide endDate(String endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Slide imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMerchantIcon() {
        return this.merchantIcon;
    }

    public Slide merchantIcon(String merchantIcon) {
        this.setMerchantIcon(merchantIcon);
        return this;
    }

    public void setMerchantIcon(String merchantIcon) {
        this.merchantIcon = merchantIcon;
    }

    public String getDealUrl() {
        return this.dealUrl;
    }

    public Slide dealUrl(String dealUrl) {
        this.setDealUrl(dealUrl);
        return this;
    }

    public void setDealUrl(String dealUrl) {
        this.dealUrl = dealUrl;
    }

    public String getTags() {
        return this.tags;
    }

    public Slide tags(String tags) {
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
        if (!(o instanceof Slide)) {
            return false;
        }
        return id != null && id.equals(((Slide) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Slide{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", subTitle='" + getSubTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", country='" + getCountry() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", merchantIcon='" + getMerchantIcon() + "'" +
            ", dealUrl='" + getDealUrl() + "'" +
            ", tags='" + getTags() + "'" +
            "}";
    }
}
