exclude :test_invalid_char, "needs investigation"
exclude :test_literal_in_conditional, "flip-flop case raises because do not implement flip-flop"
exclude :test_lparenarg, "space issue with parens in really weird syntax"
exclude :test_method_location_in_rescue, "raise is not adding call_location element for obj.location"
exclude :test_negative_line_number, "JVM emits negative like some overflow (interp fine)"
exclude :test_question, "needs investigation"
exclude :test_rescue_in_command_assignment, "needs investigation"
exclude :test_string, "lots of very specific error messages in which we differ a little"
exclude :test_truncated_source_line, "2.5 truncates long source lines...we dont yet"


