exclude :test_at, "MRI uses less precision than JRuby, which causes it to produce incorrect values"
exclude :test_future, "needs investigation"
exclude :test_marshal_nsec_191, "needs investigation"
exclude :test_num_exact_error, "error message formatting"
exclude :test_past, "needs investigation"
exclude :test_plus_type, "needs investigation"
exclude :test_reinitialize, "needs investigation"
exclude :test_strftime_flags, "month case doesn't match, needs investigation"
exclude :test_strftime_invalid_flags, "needs investigation"
exclude :test_strftime_no_hidden_garbage, "requires ObjectSpace count_objects"
exclude :test_strftime_rational, "needs investigation"
exclude :test_strftime_seconds_from_epoch, "Integer unification, needs Bignum support (#4687)"
exclude :test_strftime_too_wide, "needs investigation"
exclude :test_strftime_year, "Integer unification, needs Bignum support (#4687)"
exclude :test_to_f_accuracy, "with single division this one breaks somehow"
exclude :test_utc_or_local, "needs investigation"
exclude :test_zone, "not implemented, zone = 0 should not return UTC"
exclude :test_2038, "OK: RangeError: bignum too big to convert into `long'"
