package ma.ensa.internHub.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.Data;

class ValidImageUrlValidatorTest {

    private Validator validator;
    private ValidImageUrlValidator urlValidator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        urlValidator = new ValidImageUrlValidator();

        ValidImageUrl annotation = new ValidImageUrl() {
            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public String message() {
                return "Invalid image URL";
            }

            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override
            public Class[] payload() {
                return new Class[0];
            }

            @Override
            public String[] allowedExtensions() {
                return new String[] { ".jpg", ".jpeg", ".png" };
            }

            @Override
            public long maxFileSize() {
                return 5_242_880L;
            }

            @Override
            public Class<ValidImageUrl> annotationType() {
                return ValidImageUrl.class;
            }
        };

        urlValidator.initialize(annotation);
    }

    @Test
    void shouldAllowNullUrl() {
        assertThat(urlValidator.isValid(null, null)).isTrue();
    }

    @Test
    void shouldAllowEmptyUrl() {
        assertThat(urlValidator.isValid("", null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://static.vecteezy.com/system/resources/previews/012/300/958/original/philippines-flag-free-png.png",
            "https://petapixel.com/assets/uploads/2022/06/what-is-a-jpeg-featured-1536x806.jpg",
            "https://i1.wp.com/mancofi.dk/wp-content/uploads/2018/01/pexels-photo-768932.jpeg"
    })

    void shouldValidateCorrectImageExtensions(String url) {
        assertThat(urlValidator.isValid(url, null)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://example.com/image.pdf",
            "https://example.com/image.txt",
            "https://example.com/image",
            "not-a-url"
    })
    void shouldRejectInvalidUrls(String url) {
        assertThat(urlValidator.isValid(url, null)).isFalse();
    }

    @Test
    void shouldValidateWithEntity() {
        var testEntity = new TestEntity();
        testEntity.setImageUrl(
                "https://static.vecteezy.com/system/resources/previews/012/300/958/original/philippines-flag-free-png.png");

        var violations = validator.validate(testEntity);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldRejectInvalidUrlInEntity() {
        var testEntity = new TestEntity();
        testEntity.setImageUrl("not-a-url");

        var violations = validator.validate(testEntity);
        assertThat(violations).hasSize(1);
    }

    @Data
    private static class TestEntity {
        @ValidImageUrl
        private String imageUrl;
    }
}