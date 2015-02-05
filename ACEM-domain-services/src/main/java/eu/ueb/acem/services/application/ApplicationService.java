package eu.ueb.acem.services.application;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A bean to manage the application version.
 */
@Service("applicationService")
public class ApplicationService implements InitializingBean, Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5370486400815436620L;

	/**
	 * A logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ApplicationService.class);

	/**
	 * The version as a string.
	 */
	@Value("${project.version}")
	private String version;

	/**
	 * Bean constructor.
	 */
	public ApplicationService() {
	}

	@Override
	public void afterPropertiesSet() {
		logger.info("Starting version " + getVersion() + "...");
	}

	/**
	 * @return The composed version
	 */
	public String getVersion() {
		return version;
	}

}
